package com.github.ziran_ink.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.ziran_ink.command.Command;
import com.github.ziran_ink.command.CommandResult;

import io.airlift.concurrent.Threads;

public class Demo {
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool(Threads.daemonThreadsNamed("process-input-reader-%s"));
		CommandResult result = new Command("bash", "-c").addEnvironment("PATH",
				"/usr/local/opt/ruby@2.3/bin:/Users/xuzewei/java/script:/Users/xuzewei/Library/Android/sdk/platform-tools:/Users/xuzewei/Library/Android/sdk/tools:/Users/xuzewei/java/zookeeper-3.4.9/bin:/usr/local/mysql/bin:/Users/xuzewei/java/apache-maven-3.3.9/bin:/Library/Frameworks/Mono.framework/Versions/5.10.1/bin/:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/opt/X11/bin:/Library/Frameworks/Mono.framework/Versions/Current/Commands")
				.setDirectory("/Users/xuzewei/Downloads")
				.addArgs("aria2c \"https://www.easyicon.net/download/png/1149253/48/\"")
				.setTimeLimit(10, TimeUnit.SECONDS).setSuccessfulExitCodes(0).execute(executor);
		System.out.println(result.getExitCode());
		System.out.println(result.getCommandOutput());
	}
}
