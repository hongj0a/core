package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        //PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        //prototypeBean1.addCount();

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        System.out.println("clientBean1 = " + clientBean1);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        //PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        //prototypeBean2.addCount();

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        System.out.println("clientBean2 = " + clientBean2);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);

    }

    static class ClientBean{

        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
