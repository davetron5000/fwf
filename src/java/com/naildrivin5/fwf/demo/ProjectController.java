package com.naildrivin5.fwf.demo;

import java.util.*;

import com.naildrivin5.fwf.*;

/** Dummy controller. */
public class ProjectController extends ApplicationController
{
    private List<Project> itsProjects;

    /** Get the list of projects.  
     * @return the list of projects.
     * */
    public List<Project> getProjects()
    {
        return itsProjects;
    }

    /** index the list of projects. */
    public void index()
    {
        itsProjects = new ArrayList<Project>();
        Project p;

        p = new Project();
        p.setName("foo");
        p.setDescription("Some project called foo");
        itsProjects.add(p);

        p = new Project();
        p.setName("bar");
        p.setDescription("Some project called bar");
        itsProjects.add(p);

        p = new Project();
        p.setName("BOOYAH!");
        p.setDescription("Oh baby, this is Some project!");
        itsProjects.add(p);
    }
}
