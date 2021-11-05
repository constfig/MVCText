package cn.moquan.service;

import cn.moquan.bean.*;
import cn.moquan.dao.StudentDao;
import cn.moquan.util.CommonResponseBody;
import cn.moquan.util.RollBackException;
import cn.moquan.util.StatusNumber;
import cn.moquan.util.ThrowExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * describe
 *
 * @author wangyuanhong
 * @date 2021/10/19
 */
@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;
    @Autowired
    StudentTeacherService studentTeacherService;
    @Autowired
    ClassGradeService classGradeService;
    @Autowired
    ClassGradeTeacherService classGradeTeacherService;
    @Autowired
    TeacherService teacherService;

    public List<Student> getStudent(Student studentInfo) {

        return studentDao.getStudent(studentInfo);
    }

    public CommonResponseBody insertStudent(List<Student> studentInfoList) {

        boolean result = studentDao.insertStudent(studentInfoList);

        return insertLinkedStudentTeacher(studentInfoList, result);
    }

    public CommonResponseBody deleteStudentById(int id) {
        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(id);
        deleteLinkedStudentTeacher(idList, true);

        if (!studentDao.deleteStudentById(id)) {
            return new CommonResponseBody(StatusNumber.FAILED);
        }

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteStudent(Student info) {
        List<Student> studentList = getStudent(info);
        ArrayList<Integer> idList = new ArrayList<>();
        for (Student s : studentList) {
            idList.add(s.getId());
        }

        deleteLinkedStudentTeacher(idList, true);

        if(studentList.size() > 0){
            ThrowExceptionUtil.throwRollBackException(
                    studentDao.deleteStudent(info),
                    "删除学生信息失败, 请检查!"
            );
        }

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public CommonResponseBody deleteStudents(List<Integer> studentIdList) {

        CommonResponseBody responseBody = deleteLinkedStudentTeacher(studentIdList, true);

        if (responseBody.getStatus() == StatusNumber.SUCCESS && !studentDao.deleteStudents(studentIdList)) {
            throw new RollBackException("学生删除失败", new CommonResponseBody(StatusNumber.FAILED, "删除失败"));
        }

        return responseBody;
    }


    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    public CommonResponseBody updateStudent(Student info, List<Integer> idList) {

        // 获取统一信息
        String gradeName = info.getGradeName();
        String className = info.getClassName();
        String schoolName = info.getSchoolName();

        // 是否可以获取教师信息
        boolean completeInfo = gradeName == null || className == null || schoolName == null ||
                "".equals(gradeName) || "".equals(className) || "".equals(schoolName);

        if (!completeInfo) {
            // 删除所有学生与老师的关联
            deleteLinkedStudentTeacher(idList, true);
        }
        // 更新完成
        boolean result = studentDao.updateStudent(info, idList);

        // 出现空值 不更新 学生老师关联关系
        if (completeInfo) {
            return new CommonResponseBody(StatusNumber.SUCCESS, "学校班级信息不足, 请更新");
        }

        // 设置更新的学生与教师的关联 信息
        ArrayList<Student> studentArrayList = new ArrayList<>();
        for (int id : idList) {
            studentArrayList.add(
                    new Student(id, null, null, gradeName, className, null, schoolName));
        }

        // 开始添加关联关系
        return insertLinkedStudentTeacher(studentArrayList, result);
    }

    /**
     * 添加 学生与教师之间的连接关系
     *
     * @param studentInfoList 需要添加关联关系的学生信息
     * @param result          添加/更新/删除 操作的结果
     * @return Success: 200 null null / Failed: 500 "学生信息异常" errorList-异常学生信息列表
     */
    public CommonResponseBody insertLinkedStudentTeacher(List<Student> studentInfoList, boolean result) {
        List<Object> errorList = new ArrayList<Object>();

        for (Student s : studentInfoList) {

            String gradeName = s.getGradeName();
            String className = s.getClassName();
            String schoolName = s.getSchoolName();

            if (gradeName != null && className != null && schoolName != null
                    && !"".equals(gradeName) && !"".equals(className) && !"".equals(schoolName)) {

                // 获取班级号
                ClassGrade classGrade = new ClassGrade();
                classGrade.setGradeName(gradeName);
                classGrade.setClassName(className);
                classGrade.setSchoolName(schoolName);

                // 通过班名 级名 获取 班号
                List<ClassGrade> classGradeList = classGradeService.getClassGrade(classGrade);
                ClassGradeTeacher classGradeTeacher = new ClassGradeTeacher();
                try {
                    classGradeTeacher.setClassId(classGradeList.get(0).getId());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    throw new RollBackException("没有 " + schoolName + " " + gradeName + " " + className + " 信息, 请检查",
                            new CommonResponseBody(StatusNumber.FAILED, schoolName + " " + gradeName + " " + className + "不存在, 请检查"));
                }

                // 通过班级号获取教授这个班级课程的所有老师号
                List<ClassGradeTeacher> classGradeTeacherList = classGradeTeacherService.getClassGradeTeacher(classGradeTeacher);
                List<Integer> teacherIdList = new ArrayList<Integer>();
                for (ClassGradeTeacher info : classGradeTeacherList) {
                    teacherIdList.add(info.getTeacherId());
                }

                // 添加学生老师关联关系
                if (!studentTeacherService.insertStudentTeacher(new StudentTeacher(s.getId(), 0, s.getSchoolName()),
                        teacherIdList)) {
                    errorList.add(s);
                }
            }// if end
        }// for end

        if (errorList.size() > 0 || !result) {
            throw new RollBackException("学生信息异常",
                    new CommonResponseBody(StatusNumber.FAILED, "学生信息异常", errorList));
        }

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    /**
     * 删除 学生与教师之间的关联关系
     *
     * @param idList 学生id号
     * @param result 删除操作的结果
     * @return CommonResponseBody Success: 200 null null / Failed: 500 "学生信息删除失败" errorList-学生信息删除失败的列表
     */
    public CommonResponseBody deleteLinkedStudentTeacher(List<Integer> idList, boolean result) {

        for (int id : idList) {
            // 设置StudentTeacher信息
            StudentTeacher info = new StudentTeacher();
            info.setStudentId(id);
            // 根据 学生id 获取学生信息
            Student studentById = getStudentById(id);
            if (studentById == null) {
                continue;
            }

            info.setSchoolName(studentById.getSchoolName());

            // 根据 学生id 删除 关系
            if(studentTeacherService.getStudentTeacher(info).size() > 0){
                studentTeacherService.deleteStudentTeacher(info, null);
            }
        }

        if (!result) {
            throw new RollBackException("学生信息删除失败",
                    new CommonResponseBody(StatusNumber.FAILED, "学生信息删除失败"));
        }

        return new CommonResponseBody(StatusNumber.SUCCESS);
    }

    public boolean updateStudentCommon(Student newInfo, Student oldInfo) {

        List<Student> studentList = getStudent(oldInfo);
        String schoolName = oldInfo.getSchoolName();
        String gradeName = oldInfo.getGradeName();
        String className = oldInfo.getClassName();

        for (Student s : studentList) {
            StudentTeacher studentTeacher = new StudentTeacher(s.getId(), 0, schoolName);
            // 通过学生信息 删除
            if (studentTeacherService.getStudentTeacher(studentTeacher).size() > 0) {
                studentTeacherService.deleteStudentTeacherUseInfo(studentTeacher);
            }
        }

        // 添加关联关系
        for (Student s : studentList) {
            // 通过班级获取教师
            ClassGrade classGrade = new ClassGrade(gradeName, className, schoolName);
            List<ClassGrade> classGradeList = classGradeService.getClassGrade(classGrade);
            if (classGradeList.size() == 0) {
                // 没有班级不添加
                break;
            }
            // 查找关联
            classGrade = classGradeList.get(0);
            List<ClassGradeTeacher> classGradeTeacherList = classGradeTeacherService.getClassGradeTeacher(
                    new ClassGradeTeacher(0, classGrade.getId(), 0, classGrade.getSchoolName()));

            // 使用班级教师关联添加学生教师关联
            for (ClassGradeTeacher info : classGradeTeacherList) {
                // 添加关系
                if (studentTeacherService.getStudentTeacher(
                        new StudentTeacher(s.getId(), info.getTeacherId(), info.getSchoolName())).size() == 0) {
                    studentTeacherService.insertOnceStudentTeacher(
                            new StudentTeacher(s.getId(), info.getTeacherId(), info.getSchoolName()));
                }
            }
        }

//        // 如果只更新班级号, 不进行 学生教师关系级联
//        if(!(newInfo.getClassName() == null && newInfo.getGradeName() == null && newInfo.getClassroomRealId() != null)){
//            // 检查信息完整 不完整不需要进行删除关系
//            if(gradeName != null && !"".equals(gradeName) && className != null && !"".equals(className)){
//                for(Student s : studentList){
//                    // 通过学生信息 删除
//                    StudentTeacher studentTeacher = new StudentTeacher(s.getId(), 0, schoolName);
//                    studentTeacherService.deleteStudentTeacherUseInfo(studentTeacher);
//                }
//            }
//
//            // 添加关联关系
//            for(Student s : studentList){
//                // 通过班级获取教师
//                ClassGrade classGrade = new ClassGrade(gradeName, className, schoolName);
//                List<ClassGrade> classGradeList = classGradeService.getClassGrade(classGrade);
//                if (classGradeList.size() == 0){
//                    // 没有班级不添加
//                    break;
//                }
//                // 查找关联
//                classGrade = classGradeList.get(0);
//                List<ClassGradeTeacher> classGradeTeacherList = classGradeTeacherService.getClassGradeTeacher(
//                        new ClassGradeTeacher(0, classGrade.getId(), 0, classGrade.getSchoolName()));
//
//                // 使用班级教师关联添加学生教师关联
//                for (ClassGradeTeacher info: classGradeTeacherList){
//                    // 添加关系
//                    studentTeacherService.insertOnceStudentTeacher(
//                            new StudentTeacher(0, s.getId(), info.getTeacherId(), info.getSchoolName()));
//                }
//            }
//        }

        // 更新学生信息
        return studentDao.updateStudentCommon(newInfo, oldInfo);
    }

    public boolean updateStudentCommonNotCascade(Student newInfo, Student oldInfo) {

        return studentDao.updateStudentCommon(newInfo, oldInfo);
    }

}
