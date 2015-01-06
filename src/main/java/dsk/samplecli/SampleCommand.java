package dsk.samplecli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;

public class SampleCommand implements CommandLineRunner {

    @Override
    public void execute(String... args) throws CommandLineException, ParseException {
        Options options = new Options();
        @SuppressWarnings("static-access")
        Option sampleOption = OptionBuilder
                .hasArg()
                .withArgName("サンプルコマンド")
                .isRequired()
                .withDescription("サンプルコマンドです")
                .withLongOpt("sample")
                .create('s');
        options.addOption(sampleOption);
        Parser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption('s')) {
                System.out.printf("arg: 's', value: %s\n", cmd.getOptionValue('s', ""));
            } else {
                throw new CommandLineException("sは必須です");
            }
        } catch (org.apache.commons.cli.ParseException e) {
            throw new ParseException(e.getLocalizedMessage());
        }
    }
}
