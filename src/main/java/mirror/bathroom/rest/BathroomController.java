package mirror.bathroom.rest;

import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.BathroomService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
public class BathroomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BathroomController.class);

    private BathroomService bathroomService;

    public BathroomController(BathroomService bathroomService) {
        this.bathroomService = bathroomService;
    }

    @RequestMapping(value = "/bathroom", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public BathroomResponse getBathroomState() {
        return bathroomService.getBathroomState();
    }

    @RequestMapping(value = "/bathroom/signal-urgent", method = RequestMethod.GET)
    @ResponseBody
    public void signalUrgent() {
        bathroomService.signalUrgent();
    }

    @MessageMapping("/bathroom-message")
    @SendTo("/topic/bathroom/get")
    public BathroomResponse bathroomState() {
        return bathroomService.getBathroomState();
    }

}
