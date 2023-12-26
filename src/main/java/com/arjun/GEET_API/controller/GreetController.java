package com.arjun.GEET_API.controller;

import com.arjun.GEET_API.model.Ticket;
import com.arjun.GEET_API.model.TicketInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class GreetController {

    @GetMapping("/greet")
    public String getGreetMsg(Model model){

        String msg  = "Good Morning";
        TicketInfo[] ticketInfos = TicketInfo.values();

        // create a map to store enum values and their names
        Map<TicketInfo, String> ticketNames =
                Arrays.stream(ticketInfos)
                        .collect(Collectors.toMap(
                                ticketStation -> ticketStation,
                                ticketStation -> ticketStation.name()
                        ));

       //convert the map to a sorted list
        List<Map.Entry<TicketInfo,String>> sortedTicketNames =
                new ArrayList<>(ticketNames.entrySet());

        Collections.sort(sortedTicketNames, new Comparator<Map.Entry<TicketInfo, String>>() {
            @Override
            public int compare(Map.Entry<TicketInfo, String> o1, Map.Entry<TicketInfo, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        sortedTicketNames.forEach(tickets -> System.out.println("Key::" +tickets.getKey()+  "Values:"  +tickets.getValue() ));

        model.addAttribute("sortedTicketNames", sortedTicketNames);

        return msg;
    }

    @GetMapping("/tickets")
    public String ticketDashboard(Model model){

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket("121","3:00PM",
                "TUNDLA JN","ALJN-TDL",
                "21-11-2023"
        ));
        tickets.add(new Ticket("122","4:00PM",
        "NDLS","NDLS-ASR",
                "22-11-2023"
        ));
        tickets.add(new Ticket("123","4:00PM",
                "NDLS","NDLS-LEH",
                "22-11-2023"
        ));

        tickets.add(new Ticket("124","4:00PM",
                "NDLS","NDLS-CSMT",
                "22-11-2023"
        ));
        List<List<Ticket>> listOfTicket = new ArrayList<>();
        listOfTicket.add(tickets);

        listOfTicket.forEach( ticketsData -> {
            ticketsData.forEach( ticketsList -> System.out.println(
                    ticketsList.getTicketId()
            ) );
        });
        model.addAttribute( "listOfTicket" , listOfTicket);
        return "tickets/ticket";

    }








}
