package fi.jamk.sgky_golfcourses_json_example;

import java.util.List;

/**
 * Created by Leo on 07.11.2016.
 */

import java.util.ArrayList;
import java.util.List;

public class GolfCourse {

    public String name;
    public String address;
    public String phonenr;
    public String email;
    public String image;
    public String text;
    public String web;
    public Double lat;
    public Double lng;

    public GolfCourse(String name, String address, String phonenr, String email, String image, String text, String web, Double lat, Double lng){
        this.image = image;
        this.name = name;
        this.address = address;
        this.phonenr = phonenr;
        this.email = email;
        this.text = text;
        this.web = web;
        this.lat = lat;
        this.lng = lng;
    }


    private List<GolfCourse> golfcourses;

    public GolfCourse(){
        golfcourses = new ArrayList<>();
    }

    public void addGolfplace(GolfCourse golfdata){
        golfcourses.add(golfdata);
    }

    public List<GolfCourse> getGolfplaces(){
        return golfcourses;
    }

}
