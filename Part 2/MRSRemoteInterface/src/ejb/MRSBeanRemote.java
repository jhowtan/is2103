/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;
import javax.ejb.Remove;

/**
 *
 * @author jon
 */
@Remote
public interface MRSBeanRemote {

    String assignModuleToStudent(String modCode, String matricNum);

    String assignTutorialToStudent(String modCode, String groupNum, String matricNum);

    void createLecturer(String staffNum, String title, String name, String email, String office);

    void createModule(String modCode, String modTitle, String modTime, String modVenue);

    void createStudent(String matricNum, String name, String password, String email);

    void createSynopsis(String content);

    void createTutor(String staffNum, String name, String email);

    void createTutorial(String groupNum, String time, String venue);

    List<Vector> getModules();

    boolean isNewLecturer(String staffNum);

    boolean isNewModule(String modCode);

    boolean isNewStudent(String matricNum);

    boolean isNewTutor(String staffNum);

    boolean isNewTutorial(String modCode, String groupNum);

    void linkLecturer(String staffNum);

    void linkModule(String modCode);

    void linkSynopsis();

    void linkTutor(String staffNum);

    String processAppeals();

    @Remove
    void remove();

    String removeLecturer(String staffNum);

    String removeModule(String modCode);

    String removeStudent(String matricNum);

    String removeTutor(String staffNum);

    String removeTutorial(String modCode, String groupNum);

    void reviewAppeal(String appealID, String appealStatus, String appealComments);

    String viewModule(String modCode);

    String viewTutorial(String modCode);

    Vector getStudent(String matricNum);

    boolean checkPassword(String p1, String p2);

    boolean changePassword(String matricNum, String newPassword);

    boolean updateStudentEmail(String matricNum, String newEmail);

    boolean isClash(String date1, String date2);

    List<Vector> moduleSearch(String word);

    String webAssignMod(String modCode, String matricNum);

    String webAssignTutorial(String modCode, String tutGrp, String matricNum);

    List<Vector> getRegisteredModules(String matricNum);

    List<Vector> getStudentLessons(String matricNum);

    List<Vector> getAppeals(String matricNum);

}
