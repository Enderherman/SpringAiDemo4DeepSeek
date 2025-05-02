package top.enderherman.springaidemo4deepseek;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    /**
     * ChatConfig创建的带用户消息的ChatClient
     */
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    /**
     * 字符串异步响应消息
     *
     * @param message 请求消息
     * @return 字符串响应
     */
    @GetMapping("/sendMsgAsync")
    public String sendMsgAsync(@RequestParam(
            value = "message", defaultValue = "今天是几月几号？给我讲个笑话先") String message) {
        log.info("sendMsgAsync, message:{} ", message);
        return this.chatClient
                .prompt()       //提示词
                .user(message)  //用户输入
                .call()         //调用大模型
                .content();     //模型返回
    }

    /**
     * 流式响应消息
     *
     * @param message 请求消息
     * @return 流式响应
     */
    @GetMapping(value = "sendMsgFlow", produces = "text/html;charset=utf-8")
    public Flux<String> sendMsgFlow(@RequestParam(
            value = "message", defaultValue = "今天是几月几号？给我讲个笑话先") String message) {
        log.info("sendMsgFlow, message:{} ", message);
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }


}
