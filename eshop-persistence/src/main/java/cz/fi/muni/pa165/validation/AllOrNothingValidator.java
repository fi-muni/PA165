package cz.fi.muni.pa165.validation;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class AllOrNothingValidator implements ConstraintValidator<AllOrNothing, Object>{

	private AllOrNothing annotation;
	
	@Override
	public void initialize(AllOrNothing annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(Object annotatedObject, ConstraintValidatorContext context) {
		String [] members = annotation.members();
		
		System.out.println("Members to be validated: " + Arrays.toString(members));
		
		boolean first = true;
		boolean allShouldBeNull = false;;
		
		for (String member : members){ 
			Field f;
			try {
				f = annotatedObject.getClass().getDeclaredField(member);
				f.setAccessible(true);
				
				if (first){
					allShouldBeNull = f.get(annotatedObject) == null;
					first = false;
				} else {
					if (allShouldBeNull && f.get(annotatedObject) != null )	{
						return false;
					}
					if (!allShouldBeNull && f.get(annotatedObject) == null ) {
						return false;
					}
				}				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("Error while reading member "+member + " on class " +annotatedObject.getClass().getName() );
			}
			
		}
		return true;
	}

}
