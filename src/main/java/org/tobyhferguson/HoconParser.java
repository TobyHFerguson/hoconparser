package org.tobyhferguson;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigResolveOptions;

public class HoconParser {

  public static void main(String[] args) throws ParseException {
    Options options = new Options();
    Option comment = new Option("c", "comment", false, "Turn on comments");
    Option origin = new Option("o", "origin", false, "Turn on origin comments");
    options.addOption(comment).addOption(origin);

    CommandLineParser parser = new DefaultParser();

    CommandLine line = parser.parse(options, args);

    ConfigRenderOptions renderOptions = ConfigRenderOptions.defaults().setFormatted(true).setJson(true)
        .setOriginComments(line.hasOption("o")).setComments(line.hasOption("c"));
    ConfigResolveOptions resolveOptions = ConfigResolveOptions.defaults().setUseSystemEnvironment(true);

   for (String filename : line.getArgList()) {
      File confFile = new File(filename);
      System.err.println(confFile.getAbsolutePath());
      Config config = ConfigFactory.parseFile(confFile);

      System.out.println(config.resolve(resolveOptions).root().render(renderOptions));
    }

  }
}
