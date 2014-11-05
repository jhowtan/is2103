package ejb;

import ejb.StudentEntity;
import ejb.TutorEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-10-28T20:28:25")
@StaticMetamodel(TutorialEntity.class)
public class TutorialEntity_ { 

    public static volatile SingularAttribute<TutorialEntity, Long> id;
    public static volatile SingularAttribute<TutorialEntity, String> time;
    public static volatile SetAttribute<TutorialEntity, StudentEntity> students;
    public static volatile SingularAttribute<TutorialEntity, TutorEntity> tutor;
    public static volatile SingularAttribute<TutorialEntity, String> groupNumber;
    public static volatile SingularAttribute<TutorialEntity, String> venue;

}