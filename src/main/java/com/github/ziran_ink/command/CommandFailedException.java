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

import static java.lang.String.format;

public class CommandFailedException
        extends Exception
{
	private static final long serialVersionUID = 1L;
	private final Integer exitCode;
    private final String output;
    private final Command command;

    public CommandFailedException(Command command, String message, Throwable cause)
    {
        super(exceptionMessage(command, message, cause), cause);
        this.command = command;
        exitCode = null;
        output = null;
    }

    public CommandFailedException(Command command, int exitCode, String output)
    {
        super(format("%s exited with %s%n%s", command.getCommand(), exitCode, output));
        this.command = command;
        this.exitCode = exitCode;
        this.output = output;
    }

    public Command getCommand()
    {
        return command;
    }

    public boolean exited()
    {
        return exitCode != null;
    }

    public Integer getExitCode()
    {
        return exitCode;
    }

    public String getOutput()
    {
        return output;
    }

    private static String exceptionMessage(Command command, String message, Throwable cause)
    {
        String s = (cause == null) ? "" : (": " + cause.getMessage());
        return format("%s %s%s", command.getCommand(), message, s);
    }
}
