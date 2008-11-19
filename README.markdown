Ultimately, this is not possible in the way I envisioned.

The request/model/controller bit works great and can be done very easily.

The view is another matter.  JSP and Java are just not great technologies.  I can see some of the point of tag libraries, as they abstract some casting and other machinations required to get data into the JSP.

I think it's still possible to do something better, but it would require creating a custom-build JSP compiler that knew about the rest of the framework (or least was more flexible).

Ultimately, what you want is:

 public class TaskController extends ApplicationController
 {
     public void setName(String name) { this.name = name; }

     public List<Task> getTasks() { return this.items; }

     public void index()
     {
         this.items = getListOfAllTasksSomehow();
     }
 }

And then, in `view/task/index.jsp`, be able to do something like this:

 <h1>Tasks</h1>
 <ul>
 <% for (Task task: tasks)
    {
        <li><%= task.toString() %></li>
    }
 %>
 </ul>

The only way to do that is to dynamically introduce scripting variables based on the controller.  Basically, the JSP compiler would need to have some sort of hook to allow a mediator class to create scripting variables based on the controller; copying it's values/references over.

This would be significant work, mostly in understand an existing JSP compiler and then augmenting it with what is needed.
