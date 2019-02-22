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

import static java.util.Objects.requireNonNull;

public class CommandResult
{
    private final int exitCode;
    private final String commandOutput;

    public CommandResult(int exitCode, String commandOutput)
    {
        this.exitCode = exitCode;
        this.commandOutput = requireNonNull(commandOutput, "commandOutput is null");
    }

    public int getExitCode()
    {
        return exitCode;
    }

    public String getCommandOutput()
    {
        return commandOutput;
    }
}
