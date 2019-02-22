/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ziran_ink.command;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import static java.util.Objects.requireNonNull;

class ProcessCallable
        implements Callable<CommandResult>
{
    private final Command command;
    private final Executor executor;

    public ProcessCallable(Command command, Executor executor)
    {
        this.command = requireNonNull(command, "command is null");
        this.executor = requireNonNull(executor, "executor is null");
    }

    @Override
    public CommandResult call()
            throws CommandFailedException, InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(command.getCommand());
        processBuilder.directory(command.getDirectory());
        processBuilder.redirectErrorStream(true);
        processBuilder.environment().putAll(command.getEnvironment());

        // start the process
        Process process;
        try {
            process = processBuilder.start();
        }
        catch (IOException e) {
            throw new CommandFailedException(command, "failed to start", e);
        }

        OutputProcessor outputProcessor = null;
        try {
            // start the output processor
            outputProcessor = new OutputProcessor(process, executor);
            outputProcessor.start();

            // wait for command to exit
            int exitCode = process.waitFor();

            String out = outputProcessor.getOutput();
            // validate exit code
            if (!command.getSuccessfulExitCodes().contains(exitCode)) {
                throw new CommandFailedException(command, exitCode, out);
            }
            return new CommandResult(exitCode, out);
        }
        finally {
            try {
                process.destroy();
            }
            finally {
                if (outputProcessor != null) {
                    outputProcessor.destroy();
                }
            }
        }
    }
}
