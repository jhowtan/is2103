package ejb;

import ejb.LecturerEntity;
import ejb.StudentEntity;
import ejb.SynopsisEntity;
import ejb.TutorialEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-10-28T20:28:25")
@StaticMetamodel(ModuleEntity.class)
public class ModuleEntity_ { 

    public static volatile SingularAttribute<ModuleEntity, Long> id;
    public static volatile SingularAttribute<ModuleEntity, String> time;
    public static volatile SetAttribute<ModuleEntity, StudentEntity> students;
    public static volatile CollectionAttribute<ModuleEntity, TutorialEntity> tutorials;
    public static volatile SingularAttribute<ModuleEntity, SynopsisEntity> synopsis;
    public static volatile SingularAttribute<ModuleEntity, String> moduleCode;
    public static volatile SingularAttribute<ModuleEntity, String> moduleTitle;
    public static volatile SingularAttribute<ModuleEntity, String> venue;
    public static volatile SingularAttribute<ModuleEntity, LecturerEntity> lecturer;

}