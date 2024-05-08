package edu.java.bot.Controllers;

import edu.java.bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lab6")
public class MessageController {
    private final Bot bot;

    @PostMapping("/{id}/{file}")
    public void addFile(@PathVariable Long id, @PathVariable String file) {
        bot.process("save", id, file);
    }
}
