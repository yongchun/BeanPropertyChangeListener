import annother.ObjectPropertyDiffer;
import annother.ObjectPropertyDiffers;
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

        Student student2 = (Student) interceptor.createProxy(Student.class);
        student2.setName("afterName");
        student2.setAge(1500);
        student2.setDesc("afterTest");

        ObjectPropertyDiffer differ = ObjectPropertyDiffers.getObjectDiffer(Student.class);
        differ.diff(student, student2, p -> {
            System.out.println(p.getOldValue() + "=======>" + p.getNewValue());
        });
    }
}

