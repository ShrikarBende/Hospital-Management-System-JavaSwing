package com.hospital.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

public class ImageSaver {
    public static void main(String[] args) {
        // Base64 encoded image data for each icon
        String patientBedIcon = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAADdgAAA3YBfdWCzAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAJESURBVGiB7Zm9axRBGMZ/z3kXEggcEhC0EGysrAQLQbCxsBEE/4FgYyUipBAE/wI7wUoQxMbSKoWFjYVgIQqiYKGFhYXEgEaJl7vH4nYPdvf2I7PZvQn7wBR3O+/M+8w338sCiqIoiqIoiqIoiqJMDSbG6CxwFjgKzAP7gR3AFLAJfAU+AO+B18Br4FeS4EeBK8AFYHeEzU/gKfAAeAb8jgswC7wEtgL/NoGnwJEY/1PANeB7hP134DpwICb+UWA5wn4LeBYVfAewFON0G3gDXAT2NvjNAeeAJ8DvBh/vgVNN/heAjQb7H8DFuOQvxQS/C5zOiD0DPGrwvw7MJPg/BzYa/N+NyeB0TPBPZC/YvcAn4f8bsC/B/37gp/D/HNidFvxwTPBbCQlcFf5vE/zPCf83kgIbY4LfSEigEP6vEvy/Ef5XkoKOxQT/kJDAEeG/QvBfEf5HkwLOxwR/npDAWeF/k+C/KfzPJwVcigl+KyGBq8L/dYL/NeH/UlLAhZjgHxMSWBD+nwj+n4X/QlLAizHBVxMSuCP83yf43xP+q0kBH8cEf5+QwGXh/y7B/63wv5wU8F1M8HcJCSwK/zcJ/q+E/2JSwM2Y4K8SErhO8P8o/N8l+L8U/q+TAm7EBH+RkMAZ4b9G8F8X/meSAq7FBH+WkMAh4b9J8N8S/oeSAi7HBH+ckMBe4f+L4P9b+O9JCngmJvjDhASmhf8PQvxN4T+dFHA6JvjdhASmhP8XIf4L4Z+lHv0PiqIoiqIoiqIoiqL8H/wBvdJC5Ary8W0AAAAASUVORK5CYII=";
        
        String doctorIcon = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAADdgAAA3YBfdWCzAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAKZSURBVGiB7ZlNaBNBGIbfmd1NtmpCgiCCBw8ePHjx4kFQEMSDFw/iQfAiePEgXjwI4kHw4EE8eBDEkyB48OBBPAiCFxE8iCAIHjx48OBBgqKCSWy7O+NhN2b3Z7Ld7G6T0geWZmfm+973md35ZgYwGAwGg8FgMBgMBoOhUVCjE9A0bRiABUBhjI0wxrYDGGSMbQewBKCXUuoFQXAfwCyl9COl9COAj0EQzDLGiKqqPQAopVRRFGUFgA9gKaU0hRhjXQAGGGMWpXQbgD4AvQB6ALQDaAHQAqAZQJP6kQFgBYAfhuGSqqpLAL4D+AZgAcBXAF8AfAbwiTH2WVXVBQALjLGvQRB4iqK0AugE0AWgE0A3gG0AtgLoALAFQCuAZgDNakyTGrMJgALAVyNYBuABWAbwE8APNaYQc0FNqFXtawPQrsa0qjFWGNMEQFVjKGNMEWMYY4QxRhljgTqHQI0JwjBcDcNwBYCvxizVMwFN01oZY0MAxhhjY4qiHAbQn9VJGIZzYRi+BnAfwKSqqvcATFJKHyiK8gjAYwBPADyllD6nlM4AeKEoymtK6RyAeQBvAbwD8B7AhwSfDwA+AZgD8ArALIDnAJ4CeEApnaKUPgRwD8AkgLuU0jsA7gKYoJROqONvqeNvAJgAcAvAbQDXAVwDcBXAFQCXAVwCcBHABQDnAZwDcBbAGQCnAZwCcBLACQDHARwDcBTAEQCHARwCcBDAOICDtUxA07RhAKcAnANwgTF2iTF2BcB1xthNxtgdxtgkY+wBY+wRY2yaMfaMMfaSMfaaMfaGMTbHGHsH4D1jbJ4x9pEx9pkx9oUxtsAY+8YYWwSwxBhbZoytqP8VQhiGvqqqvqqqgaqqIWOMhGFIwzCkjDFKKaWMMcIYI4wxQgihAEgYhiQIAhIEAQmCgPi+T3zfJ57nEc/ziOu6xHVd4jgOcRyHLC8vk5WVFeK6LvE8j/i+TwDgL7Cio6VHSWu0AAAAAElFTkSuQmCC";
        
        String medicalRecordIcon = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAADdgAAA3YBfdWCzAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAGxSURBVGiB7Zm9TsMwFIXPvU5VVSgwMHXozMDOa/QleJE+Aq/Ql2BnYmBgQkKqKlH/XAZHVGuFpLm2E6v+pJGc5trn3NiJ4wAWi8VisVgsForrupU+7wHYBbADoA1gE0ATQAPAGoAQwBTABMAYwCOABwD3AO7SNN0vTdM9AHcA7gDcArjJ8/wawBWASwAXAM4BnAE4BXACYAjgGMAQwBGAQwCHAAYABgD6APqe5/UBHAAYBEHQB7APoAdgD8AugB0AbQAtAE0ADQDrAEIAawBCAKtVBXgAIgAxgATACoBVADGAJQALAOYBzAGYATANIAKQApgEkKgAYwBPAJ4BvAB4A/AO4APAJ4AvAN8AfgD8AvgD8J/neQFAEEIIpZRSWmtNKaWMMc4Y44wxzhjjnHPBOReMMcEYE4wxwTkXnHPBGBOcc8E5F4wxwRgTjDHBGBOUUkoppYwxRimlWmtNKKVaa00opVprTbTWRGtNtNaEMcYopZRSShnnnHPOBWNMcM4F51xwzgVjTDDGBGNMcM4FY0wwxoRpmvKyLHlZlrwoCl4UBS+KghdFwfM853me86IoeFEUvCgKXhQFL4qCl2XJy7LkZVnyPM95nuelpZz8A0cVoKWwHlC9AAAAAElFTkSuQmCC";
        
        String ambulanceIcon = "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAADdgAAA3YBfdWCzAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAJmSURBVGiB7Zm7axRRFMZ/Z3Y3YYkQfKCCYGFhYWFhYWFhIQgWFhYiWFgIFhYWFoKFhYVgYWEhCBYWFhYWFhYWgoWFhYWFhYWyD0wku+P9LO6EzGNndu7MbHbhfHCKe8853/nmzp17H6AoiqIoiqIoiqIoiqIoiqJME6YoQdO0JmNsFzgGHAKHwD6wD+wB34AvwGfgE/AR+ADsAu+Bd8A74C3wBngNvAJeMrwerwAXwHPgGfAUeAI8Bh4BD4EHwH3gHnAXuAPcBm4BN4EbwHXgGnAVuAJcBi4BF4ELwHngHLANbAFngdPAJnACOA6sAyvACrAMLAGLwAKwYBPYBuaBOWAWmAGmgTYwBUwCE0ALaAINYByoAzWgClSAMlACisAoULAJjAAWMAIMAwYwQAr0gR7QBX4DP4Hvf/zuAF+Br8A34AvwGfgEfAT2gD1gH/gBdIAu8Av4CfwCfgM/gS7QAzygD/SBAWCAFOgDvZDr0QN6aWOSBvgj6QR9kgb4I+kEfZIG+CPpBH2SBvgj6QR9kgb4I+kEfbIMGPgbMGiMuQtsAFV3KPxpAhvGmF0rbYDvr/xjYMu9NeFZAx5ba0tpA3x/5c8BT4Bl99aEZxl4Ya0tpw3w/ZVvAtvAuntrwrMOPAFaaQN8f+WrwDawBIy5tyc4Y8AycBtoZAnwPQOzwB1gHhh1b1FgRoF54C4wlyXA9wwsAveARWDYvU2BGQYWgPvAUpYA3zOwATwE1oGSe6sCUwLWgEfARpYA3zOwBTwD1twbFZg14DmwnSXA9wysAk+BVfcmBWYVeAasZgnwPQMnGa7HE+5NCswJhutxPUuAoiiKoiiKMp38BYSxz7UU1pxpAAAAAElFTkSuQmCC";

        try {
            // Save patient bed icon
            byte[] patientBedData = Base64.getDecoder().decode(patientBedIcon);
            FileOutputStream patientBedStream = new FileOutputStream(new File("src/main/resources/images/patient_bed.png"));
            patientBedStream.write(patientBedData);
            patientBedStream.close();

            // Save doctor icon
            byte[] doctorData = Base64.getDecoder().decode(doctorIcon);
            FileOutputStream doctorStream = new FileOutputStream(new File("src/main/resources/images/doctor.png"));
            doctorStream.write(doctorData);
            doctorStream.close();

            // Save medical record icon
            byte[] medicalRecordData = Base64.getDecoder().decode(medicalRecordIcon);
            FileOutputStream medicalRecordStream = new FileOutputStream(new File("src/main/resources/images/medical_record.png"));
            medicalRecordStream.write(medicalRecordData);
            medicalRecordStream.close();

            // Save ambulance icon
            byte[] ambulanceData = Base64.getDecoder().decode(ambulanceIcon);
            FileOutputStream ambulanceStream = new FileOutputStream(new File("src/main/resources/images/ambulance.png"));
            ambulanceStream.write(ambulanceData);
            ambulanceStream.close();

            System.out.println("All images have been saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 