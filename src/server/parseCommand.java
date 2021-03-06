package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

public class parseCommand {

    private static final Logger LOGGER = Logger.getLogger(parseCommand.class.getName());

    public static void parseCommand(JSONObject command, DataOutputStream output, int exchangeInterval) throws IOException, URISyntaxException, ParseException {

        JSONObject response = new JSONObject();

        if (command.containsKey("command")) {
            Server.debug("INFO" , command.get("command") + " received.");
            Server.debug("INFO" , "command message:" + command.toJSONString());
           
             
            // THE COMMANDS GET PARSED IN serverCommands
            serverCommands cmd = new serverCommands();
            switch ((String) command.get("command")) {
                case "EXCHANGE":
                    cmd.exchange(command, output, exchangeInterval);
                    break;
                case "FETCH":
                    cmd.fetch(command, output);
                    break;
                case "PUBLISH":
                    cmd.publish(command, output);
                    break;
                case "QUERY":
                    cmd.query(command, output);
                    break;
                case "REMOVE":
                    cmd.remove(command, output);
                    break;
                case "SHARE":
                    cmd.share(command, output);
                    break;

                default: { 
                    response.put("response", "error");
                    response.put("errorMessage", "invalid command");
                    output.writeUTF(response.toJSONString());
                    Server.debug("SEND", response.toJSONString());
                     

                }

            }
        } else {
            //if the command is missing 
            response.put("response", "error");
            response.put("errorMessage", "missing or incorrect type for command");
            output.writeUTF(response.toJSONString());
            Server.debug("SEND", response.toJSONString());
                    
        }

    }
}
