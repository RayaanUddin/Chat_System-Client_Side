/*Holds data being transmitted to clients from server. Can either contain a message or a command*/

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Packet class is used to hold data being transmitted to clients from server. Can either contain a message or a command.
 * @version 1.2
 */
public class Packet implements Serializable {
    final private ClientDetails senderDetails;

    private String type; // message or command, message on default

    final private String[] args;

    final private LocalDate date;

    final private LocalTime time;

    /**
     * Constructor for a packet. Sends any information related to transmission of messages or commands between clients and server.
     * Type is set to message by default. If the packet is a command, it can be set using the setAsCommand method.
     * @param senderDetails details of the client sending the command/ message.
     * @param args the command/ message arguments. Holds any values, such as the message, command values, recipient connection id etc.
     * @see #setAsCommand()
     * @return null
     */
    public Packet(ClientDetails senderDetails, String[] args) {
        this.senderDetails = senderDetails;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.args = args;
        type = "message";
    }

    public String[] getArguments() {
        return args;
    }

    public String getDateTime() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM")) + " " + time.getHour() + ":" + time.getMinute();
    }

    public Packet setAsCommand() {
        if (args.length > 0) {
            this.type = "command";
        } else {
            System.out.println("Error: Command packet has no arguments");
        }
        return this;
    }

    public ClientDetails getClientDetails() {
        return senderDetails;
    }

    public boolean type(String type) {
        return this.type.equalsIgnoreCase(type.toLowerCase()); // Not case-sensitive
    }

    /**
     * Used to print out the message to user interface
     * @return String | null
     */
    public String toString() {
        if (type("message")) {
            if (args.length > 1) {
                return senderDetails.getName() + "[" + senderDetails.getConnectionId() + ":private] : " + args[1] + "    " + getDateTime();
            } else {
                return senderDetails.getName() + "[" + senderDetails.getConnectionId() + "]: " + args[0] + "    " + getDateTime();
            }
        } else {
            return null;
        }
    }
}
