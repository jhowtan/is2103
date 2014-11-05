/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jon
 */
@Stateless
public class MRSBean implements MRSBeanRemote {

    @PersistenceContext
    private EntityManager em;

    private ModuleEntity moduleEntity;
    private SynopsisEntity synopsisEntity;
    private LecturerEntity lecturerEntity;
    private TutorEntity tutorEntity;
    private TutorialEntity tutorialEntity;
    private StudentEntity studentEntity;
    private AppealEntity appealEntity;
    private Collection<AppealEntity> appeals;
    private Set<ModuleEntity> modules;
    private Set<TutorialEntity> tutorials;

    public MRSBean() {
    }

    @Override
    public void createModule(String modCode, String modTitle, String modTime, String modVenue) {
        moduleEntity = new ModuleEntity();
        moduleEntity.create(modCode, modTitle, modTime, modVenue);
    }

    @Override
    public void createStudent(String matricNum, String name, String password, String email) {
        studentEntity = new StudentEntity();
        String hash = "";
        try {
            hash = encode(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        studentEntity.create(matricNum, name, hash, email);

        em.persist(studentEntity);
    }

    private String encode(String password) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(password.getBytes());
        //Get the hash's bytes 
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        String hashed = "";
        return hashed = sb.toString();
    }

    @Override
    public void createSynopsis(String content) {
        synopsisEntity = new SynopsisEntity();
        synopsisEntity.create(content);
        em.persist(synopsisEntity);
    }

    @Override
    public void createTutor(String staffNum, String name, String email) {
        tutorEntity = new TutorEntity();
        tutorEntity.create(staffNum, name, email);
        em.persist(tutorEntity);
    }

    @Override
    public void createTutorial(String groupNum, String time, String venue) {
        tutorialEntity = new TutorialEntity();
        tutorialEntity.create(groupNum, time, venue);
        em.persist(tutorialEntity);
    }

    @Override
    public void createLecturer(String staffNum, String title, String name, String email, String office) {
        lecturerEntity = new LecturerEntity();
        lecturerEntity.create(staffNum, title, name, email, office);
        em.persist(lecturerEntity);
    }

    @Override
    public void linkSynopsis() {
        moduleEntity.setSynopsis(synopsisEntity);
    }

    @Override
    public void linkLecturer(String staffNum) {
        lecturerEntity = lookupLecturer(staffNum);
        if (lecturerEntity == null) {
            em.remove(lecturerEntity);
        } else {
            moduleEntity.setLecturer(lecturerEntity);
            em.merge(moduleEntity);
        }
    }

    @Override
    public void linkTutor(String staffNum) {
        tutorEntity = lookupTutor(staffNum);
        if (tutorEntity == null) {
            em.remove(tutorEntity);
        } else {
            tutorialEntity.setTutor(tutorEntity);
            em.merge(tutorEntity);
        }
    }

    @Override
    public void linkModule(String modCode) {
        moduleEntity = lookupModuleFromCode(modCode);
        moduleEntity.getTutorials().add(tutorialEntity);
        em.merge(moduleEntity);
    }

    private LecturerEntity lookupLecturer(String staffNum) {
        LecturerEntity l = new LecturerEntity();
        try {
            Query q = em.createQuery("SELECT l FROM Lecturer AS l WHERE l.staffNumber=:staffNumber");
            q.setParameter("staffNumber", staffNum);
            l = (LecturerEntity) q.getSingleResult();
        } catch (EntityNotFoundException e) {
            System.out.println("ERROR: Lecturer not found. " + e.getMessage());
            em.remove(l);
            return null;
        } catch (NoResultException e) {
            System.out.println("ERROR: Lecturer does not exist. " + e.getMessage());
            em.remove(l);
            return null;
        }
        return l;
    }

    private TutorEntity lookupTutor(String staffNum) {
        TutorEntity t = new TutorEntity();
        try {
            Query q = em.createQuery("SELECT t FROM Tutor AS t WHERE t.staffNumber=:staffNumber");
            q.setParameter("staffNumber", staffNum);
            t = (TutorEntity) q.getSingleResult();
        } catch (EntityNotFoundException e) {
            System.out.println("ERROR: Tutor not found. " + e.getMessage());
            em.remove(t);
            return null;
        } catch (NoResultException e) {
            System.out.println("ERROR: Tutor does not exist. " + e.getMessage());
            em.remove(t);
            return null;
        }
        return t;
    }

    private ModuleEntity lookupModuleFromCode(String modCode) {
        ModuleEntity m = new ModuleEntity();
        try {
            Query q = em.createQuery("SELECT m FROM Module m WHERE m.moduleCode LIKE :modCode");
            q.setParameter("modCode", modCode);
            m = (ModuleEntity) q.getSingleResult();
        } catch (EntityNotFoundException e) {
            System.out.println("ERROR: Module not found. " + e.getMessage());
            m = null;
        } catch (NoResultException e) {
            System.out.println("ERROR: Module does not exist. " + e.getMessage());
            m = null;
        }
        return m;
    }

    /*private ModuleEntity lookupModuleFromLecturer(Long id) {
        ModuleEntity m = new ModuleEntity();
        try {
            Query q = em.createQuery("SELECT * FROM APP.MODULE WHERE LECTURER_ID = ID");
            q.setParameter("ID", id);
            m = (ModuleEntity) q.getSingleResult();
        } catch (EntityNotFoundException e) {
            System.out.println("ERROR: Module not found. " + e.getMessage());
            m = null;
        } catch (NoResultException e) {
            System.out.println("ERROR: Module does not exist. " + e.getMessage());
            m = null;
        }
        return m;
    }*/

    private StudentEntity lookupStudent(String matricNum) {
        StudentEntity s = new StudentEntity();
        try {
            Query q = em.createQuery("SELECT s FROM Student s WHERE s.matricNumber LIKE :matricNum");
            q.setParameter("matricNum", matricNum);
            s = (StudentEntity) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("ERROR: Student does not exist. " + e.getMessage());
            s = null;
        }
        return s;
    }

    private TutorialEntity lookupTutorial(String tutGroup) {
        TutorialEntity t = new TutorialEntity();
        try {
            Query q = em.createQuery("SELECT t FROM Tutorial t WHERE t.groupNumber LIKE :tutGroup");
            q.setParameter("tutGroup", tutGroup);
            t = (TutorialEntity) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("ERROR: Tutorial does not exist. " + e.getMessage());
            t = null;
        }
        return t;
    }

    @Override
    public boolean isNewLecturer(String staffNum) {
        LecturerEntity l = new LecturerEntity();
        l = null;
        l = lookupLecturer(staffNum);
        if (l == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewTutor(String staffNum) {
        TutorEntity t = new TutorEntity();
        t = null;
        t = lookupTutor(staffNum);
        if (t == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewStudent(String matricNum) {
        StudentEntity s = new StudentEntity();
        s = null;
        s = lookupStudent(matricNum);
        if (s == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewModule(String modCode) {
        ModuleEntity m = new ModuleEntity();
        m = null;
        m = lookupModuleFromCode(modCode);
        if (m == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isNewTutorial(String modCode, String groupNum) {
        ModuleEntity m = new ModuleEntity();
        m = lookupModuleFromCode(modCode);
        Collection<TutorialEntity> tutorialList = new ArrayList<TutorialEntity>();
        tutorialList = m.getTutorials();
        TutorialEntity[] tutorialArray = tutorialList.toArray(new TutorialEntity[tutorialList.size()]);
        String tutGrpsNum = new String();
        for (TutorialEntity tut : tutorialArray) {
            tutGrpsNum = tut.getGroupNumber();
            if (tutGrpsNum.equals(groupNum)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Vector> getModules() {
        Query q = em.createQuery("SELECT m FROM Module m");
        List<Vector> entityList = new ArrayList();
        for (Object o : q.getResultList()) {
            ModuleEntity m = (ModuleEntity) o;
            Vector entity = new Vector();
            entity.add(m.getId());
            entity.add(m.getModuleCode());
            entity.add(m.getModuleTitle());
            entity.add(m.getTime());
            entity.add(m.getVenue());
            entity.add(m.getSynopsis().getContent());
            entity.add(m.getLecturer().getTitle() + " " + m.getLecturer().getName());
            entity.add(m.getTutorials());
            //students
            entityList.add(entity);
        }
        return entityList;
    }

    @Override
    public String removeLecturer(String staffNum) {
        if (lookupLecturer(staffNum) == null) {
            return "Lecturer not found!\n";
        }
        lecturerEntity = em.find(LecturerEntity.class, lookupLecturer(staffNum).getId());
        List<ModuleEntity> currModules;
        Query q = em.createQuery("SELECT m FROM Module m");
        currModules = q.getResultList();
        if (currModules.isEmpty()) {
            em.remove(lecturerEntity);
            em.flush();
            em.clear();
            return "Lecturer is successfully deleted\n";
        } else {
            for (ModuleEntity currMod : currModules) {
                if (currMod.getLecturer().getStaffNumber().equals(staffNum)) {
                    em.clear();
                    return "Lecturer is associated with a module, cannot be deleted!\n";
                }
            }
            em.merge(lecturerEntity);
            em.remove(lecturerEntity);
            em.flush();
            em.clear();
            return "Lecturer is successfully deleted\n";
        }
    }

    @Override
    public String removeTutor(String staffNum) {
        if (lookupTutor(staffNum) == null) {
            return "Tutor not found!\n";
        }
        tutorEntity = em.find(TutorEntity.class, lookupTutor(staffNum).getId());
        List<TutorialEntity> currTuts;
        Query q = em.createQuery("SELECT t FROM Tutorial t");
        currTuts = q.getResultList();
        if (currTuts.isEmpty()) {
            em.remove(tutorEntity);
            em.flush();
            em.clear();
            return "Tutor is successfully deleted\n";
        } else {
            for (TutorialEntity currTut : currTuts) {
                if (currTut.getTutor().getStaffNumber().equals(staffNum)) {
                    em.clear();
                    return "Tutor is associated with a tutorial, cannot be deleted!\n";
                }
            }
            em.merge(tutorEntity);
            em.remove(tutorEntity);
            em.flush();
            em.clear();
            return "Tutor is successfully deleted\n";
        }
    }

    @Override
    public String removeStudent(String matricNum) {
        if (lookupStudent(matricNum) == null) {
            return "Student not found!\n";
        }
        studentEntity = em.find(StudentEntity.class, lookupStudent(matricNum).getId());
        modules = studentEntity.getModules();
        appeals = studentEntity.getAppeals();
        if (modules.isEmpty() && appeals.isEmpty()) {
            em.remove(studentEntity);
            em.flush();
            em.clear();
            return "Student is successfully deleted\n";
        } else {
            em.clear();
            return "Student is associated with a Module or Appeal, cannot be deleted!\n";
        }
    }

    @Override
    public String removeModule(String modCode) {
        if (lookupModuleFromCode(modCode) == null) {
            return "Module not found!\n";
        }
        moduleEntity = em.find(ModuleEntity.class, lookupModuleFromCode(modCode).getId());
        Set<StudentEntity> currStudents = moduleEntity.getStudents();
        if (!currStudents.isEmpty()) {
            return "Module has registered students, cannot be deleted!\n";
        }
        Collection<TutorialEntity> currentTutorials = moduleEntity.getTutorials();
        for (TutorialEntity tutorial : currentTutorials) {
            if (!tutorial.getStudents().isEmpty()) {
                return "Module has an associated tutorial with registered students, cannot be deleted!\n";
            }
        }
        em.remove(moduleEntity);
        em.flush();
        em.clear();
        return "Module is successfully deleted!\n";
    }

    @Override
    public String removeTutorial(String modCode, String groupNum) {
        if (lookupModuleFromCode(modCode) == null) {
            return "Module not found!\n";
        }
        moduleEntity = em.find(ModuleEntity.class, lookupModuleFromCode(modCode).getId());
        Collection<TutorialEntity> currTuts = moduleEntity.getTutorials();
        if (currTuts.isEmpty()) {
            return "Module has no tutorials!";
        }
        for (TutorialEntity tut : currTuts) {
            if (tut.getGroupNumber().equals(groupNum)) {
                if (!tut.getStudents().isEmpty()) {
                    return "Tutorial is registered by students, cannot be deleted!\n";
                } else {
                    moduleEntity.getTutorials().remove(tut);
                    em.remove(tut);
                    em.flush();
                    em.clear();
                    return "Tutorial is sucessfully deleted!\n";
                }
            }
        }
        return "Tutorial not found!\n";
    }

    @Override
    public String viewModule(String modCode) {
        String result;
        moduleEntity = lookupModuleFromCode(modCode);
        if (moduleEntity == null) {
            return "Module not found!\n";
        } else {
            result = "Title: " + moduleEntity.getModuleTitle() + "\n";
            result = result + "Lecturer: " + moduleEntity.getLecturer().getTitle() + " "
                    + moduleEntity.getLecturer().getName() + "\n";
            result = result + "Time: " + moduleEntity.getTime() + "\n";
            result = result + "Venue " + moduleEntity.getVenue() + "\n";
            result = result + "Synopsis: " + moduleEntity.getSynopsis().getContent() + "\n";
            result = result + "Registered Students: \n";
            Set<StudentEntity> students = moduleEntity.getStudents();
            if (students.isEmpty()) {
                result = result + "No registered students!";
            }
            for (StudentEntity student : students) {
                result = result + student.getMatricNumber() + " " + student.getName() + " " + student.getEmail() + "\n";
            }
            return result;
        }
    }

    @Override
    public String viewTutorial(String modCode) {
        String result = "";
        moduleEntity = lookupModuleFromCode(modCode);
        if (moduleEntity == null) {
            return "Module not found!\n";
        }
        Collection<TutorialEntity> currTuts = moduleEntity.getTutorials();
        if (currTuts.isEmpty()) {
            return "Module has no tutorials!\n";
        }
        for (TutorialEntity tut : currTuts) {
            result = result + tut.getGroupNumber() + " " + tut.getTime() + " "
                    + tut.getVenue() + " " + tut.getTutor().getName() + "\n";
            result = result + "Registered Students:\n";
            Set<StudentEntity> currentStudents = tut.getStudents();
            if (currentStudents.isEmpty()) {
                result = result + "Tutorial has no students!\n";
            } else {
                for (StudentEntity student : currentStudents) {
                    result = result + student.getMatricNumber() + " " + student.getName()
                            + " " + student.getEmail() + "\n";
                }
            }
        }
        return result;
    }

    @Override
    public String assignModuleToStudent(String modCode, String matricNum) {
        String result;
        moduleEntity = lookupModuleFromCode(modCode);
        if (moduleEntity == null) {
            return "Module not found!\n";
        }
        studentEntity = lookupStudent(matricNum);
        if (studentEntity == null) {
            return "Student does not exist!\n";
        }
        studentEntity.getModules().add(moduleEntity);
        moduleEntity.getStudents().add(studentEntity);
        em.persist(moduleEntity);
        em.persist(studentEntity);
        em.flush();
        em.clear();
        result = "Student is successfully assigned to module\n";
        return result;
    }

    @Override
    public String assignTutorialToStudent(String modCode, String groupNum, String matricNum) {
        String result;
        moduleEntity = lookupModuleFromCode(modCode);
        if (moduleEntity == null) {
            return "Module not found!\n";
        }
        studentEntity = lookupStudent(matricNum);
        if (studentEntity == null) {
            return "Student not found!\n";
        }
        Collection<TutorialEntity> currTuts = moduleEntity.getTutorials();
        if (currTuts.isEmpty()) {
            return "Module has no associated tutorials!\n";
        }
        for (TutorialEntity tut : currTuts) {
            if (tut.getGroupNumber().equals(groupNum)) {
                tutorialEntity = tut;
                studentEntity.getTutorials().add(tutorialEntity);
                tutorialEntity.getStudents().add(studentEntity);
                em.persist(studentEntity);
                em.persist(tutorialEntity);
                em.flush();
                em.clear();
                result = "Student is successfully assigned to tutorial!\n";
                return result;
            }
        }
        return "Tutorial does not exist!\n";
    }

    @Override
    public String processAppeals() {
        String result = "";
        Query q = em.createQuery("SELECT a FROM Appeal a");
        List<AppealEntity> appealList = q.getResultList();
        for (AppealEntity appeal : appealList) {
            if (appeal.getStatus().equals("Unread") || appeal.getStatus().equals("Processing")) {
                result = result + appeal.getId() + ": " + appeal.getTime() + ", " + appeal.getStudent().getName()
                        + ", " + appeal.getStudent().getMatricNumber() + ", " + appeal.getContent() + ".\n";
            }
        }
        if (result.equals("")) {
            return "No appeals to show!\n";
        }
        return result;
    }

    @Override
    public void reviewAppeal(String appealID, String appealStatus, String appealComments) {
        appealEntity = em.find(AppealEntity.class, appealID);
        appealEntity.setStatus(appealStatus);
        appealEntity.setComment(appealComments);
        em.persist(appealEntity);
        em.flush();
        em.clear();
    }

    @Override
    @Remove
    public void remove() {
        System.out.println("MRSBean: remove()");
    }

    @Override
    public Vector getStudent(String matricNum) {
        studentEntity = lookupStudent(matricNum);
        Vector studentData = new Vector();
        if (studentEntity != null) {
            studentData.add(studentEntity.getMatricNumber());
            studentData.add(studentEntity.getName());
            studentData.add(studentEntity.getPassword());
            studentData.add(studentEntity.getEmail());
            return studentData;
        }
        return null;
    }

    @Override
    public boolean checkPassword(String p1, String p2) {
        String hashedPublic = "";
        try {
            hashedPublic = encode(p2);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MRSBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hashedPublic.equals(p1)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String matricNum, String newPassword) {
        Query q = em.createQuery("SELECT s FROM Student s WHERE s.matricNumber = :matric");
        q.setParameter("matric", matricNum);
        studentEntity = (StudentEntity) q.getResultList().get(0);
        String hashedPassword = null;
        try {
            hashedPassword = encode(newPassword);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MRSBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hashedPassword == null) {
            System.out.println("Error: Password to be updated cannot be empty!");
            return false;
        }
        studentEntity.setPassword(hashedPassword);
        em.merge(studentEntity);
        return true;
    }

    @Override
    public boolean updateStudentEmail(String matricNum, String newEmail) {
        Query q = em.createQuery("SELECT s FROM Student s WHERE s.matricNumber = :matric");
        q.setParameter("matric", matricNum);
        studentEntity = (StudentEntity) q.getResultList().get(0);
        if (newEmail.isEmpty()) {
            System.out.println("Error: Email to be updated cannot be empty!");
            return false;
        }
        studentEntity.setEmail(newEmail);
        em.merge(studentEntity);
        return true;
    }

    @Override
    public boolean isClash(String time1, String time2) {
        String[] first = time1.split(" ");
        String[] second = time2.split(" ");
        if (first[0].equals(second[0])) {
            if (Integer.parseInt(first[1]) <= Integer.parseInt(second[1])) {
                if (Integer.parseInt(first[2]) > Integer.parseInt(second[2])) {
                    return true;
                }
                return false;
            } else if (Integer.parseInt(second[1]) <= Integer.parseInt(first[1])) {
                if (Integer.parseInt(second[2]) > Integer.parseInt(first[1])) {
                    return true;
                }
                System.out.println("MRSBean: No clash!");
                return false;
            }
        }
        System.out.println("MRSBean: No clash!");
        return false;
    }
    
    @Override
    public List<Vector> moduleSearch(String word) {
        List<Vector> modulesData = new ArrayList<>();
        List<Long> moduleIds = new ArrayList<>();
        word = word.toLowerCase();
        String compareTo;
        Query q = em.createQuery("SELECT m FROM Module m");
        for (Object o : q.getResultList()) {
            ModuleEntity m = (ModuleEntity) o;
            compareTo = m.getModuleTitle().toLowerCase();
            if (compareTo.contains(word) || word.equals("*")) {
                moduleIds.add(m.getId());
                continue;
            }
            compareTo = m.getModuleCode().toLowerCase();
            if (compareTo.contains(word)) {
                moduleIds.add(m.getId());
            }
        }
        for (Long moduleId : moduleIds) {
            moduleEntity = em.find(ModuleEntity.class, moduleId);
            Vector modInfo = new Vector();
            modInfo.add(moduleEntity.getId());
            modInfo.add(moduleEntity.getModuleTitle());
            modInfo.add(moduleEntity.getModuleCode());
            modInfo.add(moduleEntity.getTime());
            modInfo.add(moduleEntity.getVenue());
            modInfo.add(moduleEntity.getSynopsis().getContent());
            modInfo.add(moduleEntity.getLecturer().getTitle() + " " + moduleEntity.getLecturer().getName());
            List<Long> tutorialIds = new ArrayList<>();
            List<String> tuts = new ArrayList<>();
            for (TutorialEntity t : moduleEntity.getTutorials()) {
                tutorialIds.add(t.getId());
                tuts.add("Group " + t.getGroupNumber() + ": "
                        + t.getTime() + " at " + t.getVenue() + " by " + t.getTutor().getName());
            }
            modInfo.add(tutorialIds);
            List<Long> studentIds = new ArrayList();
            for (StudentEntity s : moduleEntity.getStudents()) {
                studentIds.add(s.getId());
            }
            modInfo.add(studentIds);
            modInfo.add(tuts);
            modulesData.add(modInfo);
        }
        return modulesData;
    }

    @Override
    public String webAssignMod(String modCode, String matricNum) {
        studentEntity = lookupStudent(matricNum);
        moduleEntity = lookupModuleFromCode(modCode);

        String modLectureTime = moduleEntity.getTime();
        ArrayList<String> tutorialTimes = new ArrayList<>();
        ArrayList<String> moduleTimes = new ArrayList<>();
        for (TutorialEntity t : studentEntity.getTutorials()) {
            tutorialTimes.add(t.getTime());
        }
        for (ModuleEntity m : studentEntity.getModules()) {
            if (m.equals(moduleEntity)) {
                break;
            }
            moduleTimes.add(m.getTime());
        }
        for (String tutorialTime : tutorialTimes) {
            if (isClash(modLectureTime, tutorialTime)) {
                return "UNSUCCESSFUL: There is a clash in timings with one of your tutorials on " + tutorialTime;
            }
        }
        for (String moduleTime : moduleTimes) {
            if (isClash(modLectureTime, moduleTime)) {
                return "UNSUCCESSFUL: There is a clash in timings with one of your modules on " + moduleTime;
            }
        }
        return assignModuleToStudent(modCode, matricNum);
    }

    @Override
    public String webAssignTutorial(String modCode, String tutGrp, String matricNum) {
        studentEntity = lookupStudent(matricNum);
        moduleEntity = lookupModuleFromCode(modCode);
        tutorialEntity = lookupTutorial(tutGrp);
        System.out.println("Tutorial Entity: " + tutorialEntity);

        String tutGrpTime = tutorialEntity.getTime();
        ArrayList<String> tutorialTimes = new ArrayList<>();
        ArrayList<String> moduleTimes = new ArrayList<>();
        for (TutorialEntity t : studentEntity.getTutorials()) {
            if (t.equals(tutorialEntity)) {
                break;
            }
            tutorialTimes.add(t.getTime());
        }
        for (ModuleEntity m : studentEntity.getModules()) {
            moduleTimes.add(m.getTime());
        }
        for (String tutorialTime : tutorialTimes) {
            if (isClash(tutGrpTime, tutorialTime)) {
                return "UNSUCCESSFUL: There is a clash in timings with one of your tutorials on " + tutorialTime;
            }
        }
        for (String moduleTime : moduleTimes) {
            if (isClash(tutGrpTime, moduleTime)) {
                return "UNSUCCESSFUL: There is a clash in timings with one of your modules on " + moduleTime;
            }
        }
        return assignTutorialToStudent(modCode, tutGrp, matricNum);
    }

    @Override
    public List<Vector> getRegisteredModules(String matricNum) {
        studentEntity = lookupStudent(matricNum);
        List<Vector> modulesReg = new ArrayList<>();
        for (ModuleEntity m : studentEntity.getModules()) {
            Vector modsReg = new Vector();
            modsReg.add(m.getModuleTitle());
            modsReg.add(m.getModuleCode());
            modsReg.add(m.getTime());
            modsReg.add(m.getVenue());
            modsReg.add(m.getSynopsis().getContent());
            modsReg.add(m.getLecturer().getTitle() + " " + m.getLecturer().getName());
            List<String> tutGrps = new ArrayList<>();
            List<String> tutDesc = new ArrayList<>();
            for (TutorialEntity t : m.getTutorials()) {
                tutDesc.add("Group " + t.getGroupNumber() + ": "
                        + t.getTime() + " at " + t.getVenue() + " by " + t.getTutor().getName());
                tutGrps.add(t.getGroupNumber());
                tutGrps.add(t.getTime());
            }
            modsReg.add(tutDesc);
            modsReg.add(tutGrps);
            modulesReg.add(modsReg);
        }
        return modulesReg;
    }

    @Override
    public List<Vector> getStudentLessons(String matricNum) {
        studentEntity = lookupStudent(matricNum);
        List<Vector> lessons = new ArrayList();
        for (TutorialEntity t : studentEntity.getTutorials()) {
            Vector o = new Vector();
            o.add(t.getGroupNumber());
            o.add(t.getTime());
            lessons.add(o);
        }
        for (ModuleEntity m : studentEntity.getModules()) {
            Vector o2 = new Vector();
            o2.add(m.getModuleCode());
            o2.add(m.getTime());
            lessons.add(o2);
        }
        return lessons;
    }

    @Override
    public List<Vector> getAppeals(String matricNum) {
        studentEntity = lookupStudent(matricNum);
        List<Vector> appeals = new ArrayList();
        for (AppealEntity a : studentEntity.getAppeals()) {
            Vector app = new Vector();
            app.add(a.getTime());
            app.add(a.getStatus());
            app.add(a.getContent());
            app.add(a.getComment());
            appeals.add(app);
        }
        return appeals;
    }
    
    
    
    

}
