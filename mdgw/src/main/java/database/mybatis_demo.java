package database;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.Mapper.Mapper300192;
import database.Mapper.UserMapper;
import database.POJO.message_300192;
import message.Market_300111;
import message.OrderMarket_300192;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class mybatis_demo {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(new test()));

//        objectMapper.readValue("{\"id\":1,\"name\":\"rte\",\"mdStreamID\":{\"value\":\"sad\",\"length\":3}}", test.class);
        System.out.println(objectMapper.readValue("{\"mdStreamID\":{\"value\":\"sad\",\"length\":3}}", test.class));
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        List<User> selectUser = userMapper.selectUser();
//        selectUser.forEach(System.out::println);
//
//        Mapper300192 mapper300192 = sqlSession.getMapper(Mapper300192.class);
//        mapper300192.addAll(new message_300192());
//        mapper300192.selectAll().forEach(System.out::println);
//
//        sqlSession.close();
    }
}
