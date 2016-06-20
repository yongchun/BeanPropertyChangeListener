import bean.Student;
import support.BeanMethodInterceptor;

/**
 * Created by Shannon,chen on 16/6/20.
 */
public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("beforeName");
        BeanMethodInterceptor<Student> interceptor = new BeanMethodInterceptor(student, p -> {
            System.out.println(p.getOldValue() + "=======>" + p.getNewValue());
        });

        student = (Student) interceptor.createProxy(Student.class);
        student.setName("afterName");
        student.setAge(1500);
        student.setDesc("afterTest");
    }

}

