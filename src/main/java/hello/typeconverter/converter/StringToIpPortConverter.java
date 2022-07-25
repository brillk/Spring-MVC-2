package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;


@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {


    @Override
    public IpPort convert(String source) {
        log.info("convert source={}", source);
        // "127.0.0.1:8080" -> IpPort 객체
        // : <= 이걸 기준으로 나누자
        String[] split = source.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new IpPort(ip, port);
    }
}
