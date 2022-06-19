package com.opylypiv.myhosp;

import java.util.ArrayList;

public class DoctorListSort {
    ArrayList<Doctor> anesthesiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> bacteriologist = new ArrayList<Doctor>();
    ArrayList<Doctor> cardiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> ceo = new ArrayList<Doctor>();
    ArrayList<Doctor> dentist = new ArrayList<Doctor>();
    ArrayList<Doctor> dentist_orthodontist = new ArrayList<Doctor>();
    ArrayList<Doctor> dentist_surgeon = new ArrayList<Doctor>();
    ArrayList<Doctor> dentist_therapist = new ArrayList<Doctor>();
    ArrayList<Doctor> dermatologist = new ArrayList<Doctor>();
    ArrayList<Doctor> dermatovenereologist = new ArrayList<Doctor>();
    ArrayList<Doctor> usd = new ArrayList<Doctor>();
    ArrayList<Doctor> endocrinologist = new ArrayList<Doctor>();
    ArrayList<Doctor> endoscopist = new ArrayList<Doctor>();
    ArrayList<Doctor> ginekologist = new ArrayList<Doctor>();
    ArrayList<Doctor> infectious = new ArrayList<Doctor>();
    ArrayList<Doctor> medical_director = new ArrayList<Doctor>();
    ArrayList<Doctor> narcologist = new ArrayList<Doctor>();
    ArrayList<Doctor> nephrologist = new ArrayList<Doctor>();
    ArrayList<Doctor> neurologist = new ArrayList<Doctor>();
    ArrayList<Doctor> gynecologist = new ArrayList<Doctor>();
    ArrayList<Doctor> ophthalmologist = new ArrayList<Doctor>();
    ArrayList<Doctor> traumatologist = new ArrayList<Doctor>();
    ArrayList<Doctor> otolaryngologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_gynecologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_anesthesiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_cardiorheumatologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_dentist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_dermatovenereologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_endocrinologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_neurologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_ophthalmologist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatric_psychiatrist = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatrician = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatrician_hospital = new ArrayList<Doctor>();
    ArrayList<Doctor> pediatrician_neonatologist = new ArrayList<Doctor>();
    ArrayList<Doctor> physician = new ArrayList<Doctor>();
    ArrayList<Doctor> urologist = new ArrayList<Doctor>();
    ArrayList<Doctor> physiotherapist = new ArrayList<Doctor>();
    ArrayList<Doctor> radiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> rheumatologist = new ArrayList<Doctor>();
    ArrayList<Doctor> surgeon = new ArrayList<Doctor>();


    public ArrayList<ArrayList<Doctor>> getDoctorgroups(Doctor doctor) {

        ArrayList<ArrayList<Doctor>> doctorgroups = new ArrayList<>();

        switch (doctor.getCodespec()) {
            case "anesthesiologist":
                anesthesiologist.add(doctor);
                break;
            case "bacteriologist":
                bacteriologist.add(doctor);
                break;
            case "cardiologist":
                cardiologist.add(doctor);
                break;
            case "CEO":
                ceo.add(doctor);
                break;
            case "dentist":
                dentist.add(doctor);
                break;
            case "dermatologist":
                dermatologist.add(doctor);
                break;
            case "dermatovenereologist":
                dermatovenereologist.add(doctor);
                break;
            case "endocrinologist":
                endocrinologist.add(doctor);
                break;
            case "endoscopist":
                endoscopist.add(doctor);
                break;
            case "ginekologist":
                ginekologist.add(doctor);
                break;
            case "infectious":
                infectious.add(doctor);
                break;
            case "medical_director":
                medical_director.add(doctor);
                break;
            case "narcologist":
                narcologist.add(doctor);
                break;
            case "nephrologist":
                nephrologist.add(doctor);
                break;
            case "neurologist":
                neurologist.add(doctor);
                break;
            case "ophthalmologist":
                ophthalmologist.add(doctor);
                break;
            case "traumatologist":
                traumatologist.add(doctor);
                break;
            case "otolaryngologist":
                otolaryngologist.add(doctor);
                break;
            case "pediatric_gynecologist":
                pediatric_gynecologist.add(doctor);
                break;
            case "pediatric_anesthesiologist":
                pediatric_anesthesiologist.add(doctor);
                break;
            case "pediatric_cardiorheumatologist":
                pediatric_cardiorheumatologist.add(doctor);
                break;
            case "pediatric_dentist":
                pediatric_dentist.add(doctor);
                break;
            case "pediatric_dermatovenereologist":
                pediatric_dermatovenereologist.add(doctor);
                break;
            case "pediatric_endocrinologist":
                pediatric_endocrinologist.add(doctor);
                break;
            case "pediatric_neurologist":
                pediatric_endocrinologist.add(doctor);
                break;
            case "pediatric_ophthalmologist":
                pediatric_endocrinologist.add(doctor);
                break;
            case "pediatric_psychiatrist":
                pediatric_psychiatrist.add(doctor);
                break;
            case "pediatrician":
                pediatrician.add(doctor);
                break;
            case "pediatrician_hospital":
                pediatrician.add(doctor);
                break;
            case "pediatrician_neonatologist":
                pediatrician_neonatologist.add(doctor);
                break;
            case "physician":
                physician.add(doctor);
                break;
            case "physiotherapist":
                physiotherapist.add(doctor);
                break;
            case "radiologist":
                radiologist.add(doctor);
                break;
            case "rheumatologist":
                rheumatologist.add(doctor);
                break;
            case "surgeon":
                surgeon.add(doctor);
                break;
            case "urologist":
                urologist.add(doctor);
                break;
        }
        doctorgroups.add(anesthesiologist);
        doctorgroups.add(bacteriologist);
        doctorgroups.add(ceo);
        doctorgroups.add(dentist);
        doctorgroups.add(dermatologist);
        doctorgroups.add(dermatovenereologist);
        doctorgroups.add(endocrinologist);
        doctorgroups.add(endoscopist);
        doctorgroups.add(gynecologist);
        doctorgroups.add(nephrologist);
        doctorgroups.add(neurologist);
        doctorgroups.add(ophthalmologist);
        doctorgroups.add(traumatologist);
        doctorgroups.add(otolaryngologist);
        doctorgroups.add(pediatric_gynecologist);
        doctorgroups.add(pediatric_anesthesiologist);
        doctorgroups.add(pediatric_cardiorheumatologist);
        doctorgroups.add(pediatric_dentist);
        doctorgroups.add(pediatric_dermatovenereologist);
        doctorgroups.add(pediatric_endocrinologist);
        doctorgroups.add(pediatric_neurologist);
        doctorgroups.add(pediatric_ophthalmologist);
        doctorgroups.add(pediatric_psychiatrist);
        doctorgroups.add(pediatrician);
        doctorgroups.add(pediatrician_hospital);
        doctorgroups.add(pediatrician_neonatologist);
        doctorgroups.add(physician);
        doctorgroups.add(physiotherapist);
        doctorgroups.add(radiologist);
        doctorgroups.add(rheumatologist);
        doctorgroups.add(surgeon);
        doctorgroups.add(urologist);

        return doctorgroups;

    }
}





