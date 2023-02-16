// IStudentService.aidl
package com.jobs.android.jetpacklearn;

import com.jobs.android.jetpacklearn.Student;

interface IStudentService {
    List<Student> getStudentList();
    void addStudent(in Student student);
}