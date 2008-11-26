# Fun Web Framework

*The fun is only theoretical.*

This was an experiment I did over a day and a half to see how quickly I could create an easy-to-use and easy-to-deploy Java Web Framework based on convention over configuration; to make something opinionated (basically mimicing how Rails works) in Java.  

The results were that it's easy to create a simple controller framework that deals with some of the web's annoynaces (namely mapping URIs to code as well as serialiation of data to and from strings), but creating a robust and easy to use view framework is a bit more of a herculian task.

## Background

Web development is problematic because:

* All communication between the browser and server is via text; all data must be serialized to/from strings to be used
* URLs must be mapped to code to handle them
* Generating HTML/CSS/Javascript is difficult and requires agility in order to hone in on a functional and usable design

Most Java web frameworks provide the ability to address these issues, however few (any?) provide a way to do so simple and easily and most of them are just simply not very fun to use.

## Motivation

Java Web Development is **not** fun because you must do tons of XML situps and specify lots of things that could be figured out by just following conventions.  Further, it is not fun because creating the view is much more difficult than it needs to be.

## Making it Fun

Model-View-Controller is a reasonable approach to web development, and most web frameworks use this patternt o solve the problem.  Serlvets, Struts, Spring and Seam just seem to require way too much overhead to do something that should be relatively simple (and should've been baked into the Servlet specification from the start).

### Model

Models should be any java object you want (or at least treatable that way).

### Controller

It stands to reason that all requests to a web application are of the form `*METHOD* URI` and that we can figure out everything we need from that bit of information; no XML (or, almost no XML).

    HTTP   * URI Pattern            * Method  * Effect
    Method |                        | Called  |
    -------|------------------------|---------|------------------------------------------
    GET    $ROOT/Widget               index()   Access the list of all Widgets via the WidgetController class
    GET    $ROOT/Widget/45            show()    Access the Widgets with ID 45 via the WidgetController class
    PUT    $ROOT/Widget/45            update()  Update Widget with ID 54 via the WidgetController class, using
           ?name=New+Name&price=13              setName(String) and setPrice(int) on that class to convey the parameters.
    POST   $ROOT/Widget/              create()  Create a new Widget using the WidgetController class
    DELETE $ROOT/Widget/45            destroy() Delete the Widget with id 45 using the WidgetController class
    GET or $ROOT/Widget/45/doit       doit()    Doit (whatever that means) the Widget with id 45 using the WidgetController class
    POST

This is incredibly easy to accomplish, and the only configuration that needs to occur is:

* Configure a Servlet in <tt>web.xml</tt>
* Configure that Servlet with the packages in which to search for Controllers

Honestly, the Servlet spec should've operated in a similar fashion from the get-go.  Would it have really been too much to ask to assume that a class that implemented servlet named <tt>FooServlet</tt> be automatically mapped to <tt>/foo</tt> unless otherwise specified?

Anyway, this is very much a copy of how Rails works; a controller can access parameters via a <tt>params</tt> hash that returns parameters as strings, or by implementing <tt>setXXX</tt> methods that can take types (e.g. a <tt>setAge(int)</tt> will get called for a paremter in the querystring of <tt>age=45</tt>).  The controller can then do whatever it needs to do.

After processing, the controller has three options: 

* **Do nothing** - the framework sends the user to <tt>views/$MODELNAME/$ACTIONNAME</tt>, i.e.  <tt>views/Widget/index.jsp</tt> for a <tt>GET</tt> to <tt>Widget/45</tt> (very similar to Rails).
* **Use an alternate view** - the framework finds a JSP names for the requested view inside the model's view directory
* **Forward to another action** - takes all request info and sends it to another action for further processing

Again, this is all incredibly simple to accomplish, and the current codebase does this in under 300 lines of code.

### View

This is where the entire thing breaks down.  Ultimately, JSP is not up to the task, and the compiled/statically-typed nature of Java prevents us from make a robust **and** easy-to-use view layer.  In fact, the way JSPs work is actually **worse** than a totally pre-compiled solution (like GWT).

At any rate, the ideal would be that:

* Every bit of data available to the controller is available to the view *without casting* and *without doing anything special in the controller*.
* No need for tag libraries (with sophistiacted view technology, scriptlets and EL can do everything you need much more clearly)
* Numerous utility methods to construct URLs, escape HTML, etc.

Tag libraries are a particularly nasty hack, because they result in one or more new languages being introduced into an already crowded view technology (which requires at least THREE languages already).  Further, the syntax is extremely verbose with no real benefit, **save the need to avoid casting everything**.  

Even without tag libraries, we can avoid some casting with the baffling `jsp:useBean` construct, however this is really not sufficient.

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

This would require extending JSP to give Java the appearance of being a dynamic languages for the view layer.

A better solution might be to replace JSP entirely.  GWT is a nice approach, but a dynamic "change/reload" solution would be ideal, because you don't really want to wait for a recompile when tweaking the UI.

## Conclusions

My hope of a small and feature-filled Java Web Framework is not currently to be.  While the Servlet spec is bafflingly limited; it can be used easily to create a small, easy-to-use controller framework.  JSP, on the other hand, is just a really bad solution to creating dynamic HTML.  Tag libraries are a hack that transfer the pain from one place to another, but a better solution is to either author the entire view in Java or use a dynamic/scripting language.

## Areas for further research

[JRuby](http://wiki.jruby.org/wiki/Main_Page) provides a means to have Ruby scripting interact with Java, via the [scripting extension](https://scripting.dev.java.net/) to Java.  This could essentially allow for using a scripting language for the view (even straight Rails stuff).


