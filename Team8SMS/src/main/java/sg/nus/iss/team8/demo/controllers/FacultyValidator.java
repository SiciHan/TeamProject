package sg.nus.iss.team8.demo.controllers;

import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.team8.demo.models.Courserun;
import sg.nus.iss.team8.demo.models.Department;
import sg.nus.iss.team8.demo.models.Faculty;
import sg.nus.iss.team8.demo.models.Semester;
import sg.nus.iss.team8.demo.models.Student;
@Component
public class FacultyValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Faculty.class.equals(clazz)||Semester.class.equals(clazz) 
				|| Student.class.equals(clazz)||PageImpl.class.equals(clazz)
				||Courserun.class.equals(clazz)||Department.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		//if the object is a faculty
		if(target instanceof Faculty) {
			Faculty f = (Faculty) target;
			if (f.getDepartment().getDepartmentId()==0) {
				errors.rejectValue("department", "emptyDepartment",new Object[] {"department"}, "department must be selected");
			}
			if (f.getStatus().getStatus()==0) {
				errors.rejectValue("status", "emptyStatus",new Object[] {"status"}, "status must be selected");
			}
			if (f.getName().isBlank()) {
				errors.rejectValue("name", "emptyName", new Object[] {"name"}, "Name cannot be blank");
			}
			if (f.getFacultyId() < 0) {
				errors.rejectValue("facultyId", "negativeNumber", new Object[] {"facultyId"}, "Faculty Id cannot be negative");
			}
			
		}
		if(target instanceof Semester) {
			Semester s= (Semester) target;
		}
		if(target instanceof Student) {
			Student st = (Student) target;
		}
		if(target instanceof Department) {
			Department d= (Department) target;
			if (d.getName().isBlank()) {
				errors.rejectValue("name", "emptyName", new Object[] {"name"}, "Name cannot be blank");
			}
		}
	}

}
