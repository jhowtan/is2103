package ejb;

import ejb.StudentEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-10-28T20:28:25")
@StaticMetamodel(AppealEntity.class)
public class AppealEntity_ { 

    public static volatile SingularAttribute<AppealEntity, Long> id;
    public static volatile SingularAttribute<AppealEntity, String> content;
    public static volatile SingularAttribute<AppealEntity, String> time;
    public static volatile SingularAttribute<AppealEntity, StudentEntity> student;
    public static volatile SingularAttribute<AppealEntity, String> status;
    public static volatile SingularAttribute<AppealEntity, String> comment;

}