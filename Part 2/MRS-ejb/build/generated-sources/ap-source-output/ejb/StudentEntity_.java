package ejb;

import ejb.AppealEntity;
import ejb.ModuleEntity;
import ejb.TutorialEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-10-28T20:28:25")
@StaticMetamodel(StudentEntity.class)
public class StudentEntity_ { 

    public static volatile SingularAttribute<StudentEntity, Long> id;
    public static volatile SetAttribute<StudentEntity, TutorialEntity> tutorials;
    public static volatile SingularAttribute<StudentEntity, String> email;
    public static volatile SingularAttribute<StudentEntity, String> name;
    public static volatile SingularAttribute<StudentEntity, String> matricNumber;
    public static volatile CollectionAttribute<StudentEntity, AppealEntity> appeals;
    public static volatile SetAttribute<StudentEntity, ModuleEntity> modules;
    public static volatile SingularAttribute<StudentEntity, String> password;

}