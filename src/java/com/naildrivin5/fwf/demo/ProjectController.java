package com.naildrivin5.fwf.demo;

import java.util.*;

import com.naildrivin5.fwf.*;

/** Dummy controller. */
public class ProjectController extends ApplicationController
{
    private List<Project> itsProjects;

    public List<Project> getProjects()
    {
        return itsProjects;
    }

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
