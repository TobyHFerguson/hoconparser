package org.tobyhferguson;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigResolveOptions;

public class HoconParser {

  public static void main(String[] args) throws ParseException {
    Options options = new Options();
    Option comment = new Option("c", "comments", false, "Turn on comments");
    Option help = new Option("h", "help", false, "Print this help");
    Option origin = new Option("o", "origins", false, "Turn on origin comments");
    options.addOption(comment).addOption(origin).addOption(help);
    try {
      CommandLine line = new DefaultParser().parse(options, args);
      List<String> arg_list = line.getArgList();
      
      if (line.hasOption('h') || arg_list.isEmpty()) {
        printHelp(options);
      } else {
        File confFile = new File(arg_list.get(0));
        Config config = ConfigFactory.parseFile(confFile);  
        System.out.println(config.resolve(getResolveOptions()).root().render(getRenderOptions(line)));
      }
    } catch (UnrecognizedOptionException ure) {
      System.err.println("ERROR: " + ure.getMessage() +"\n");
      printHelp(options);
    }
  }

  private final static String cmdLine = "hocon [-h] | [-c | --comments ] [ -o | --origin ] hocon_file";
  private final static String header = "Print the hocon file in json format, after all includes and substitutions";
  private final static int width = 80;
  private final static int leftPad = 2;
  private final static int descPad = 2;
  private final static String footer = "";

  private static void printHelp(Options options) {
    PrintWriter pw = new PrintWriter(System.err);
    new HelpFormatter().printHelp(pw, width, cmdLine,
        header, options, leftPad, descPad, footer, false);
    pw.flush();
  }

  private static ConfigRenderOptions getRenderOptions(CommandLine line) {
    return ConfigRenderOptions.defaults().setFormatted(true).setJson(true).setOriginComments(line.hasOption("o"))
        .setComments(line.hasOption("c"));
  }

  private static ConfigResolveOptions getResolveOptions() {
    return ConfigResolveOptions.defaults().setUseSystemEnvironment(true);
  }
}
