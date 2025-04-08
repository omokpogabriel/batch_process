package com.gabby.spring.batch.config;

import com.gabby.spring.batch.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class MultiThreadedItemProcessor  implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        System.out.println("THIS IS THE BEST THING");
        return student;
    }
}
