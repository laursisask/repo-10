***diagrams.net build of PlantUML***

Strips out eggs, games, licensing, GPL licensed code, etc. Build of server version found under [server repo packages section](https://github.com/jgraph/plantuml/packages).

***diagrams.net specific changes:***

In order for deploying our custom PlantUML jars to Github, change the version in build.xml (in `mvn_windows` and `mvn_unix` targets). Then in PlantUML Server repository, change the dependency version to match the new version (in `pom.xml` find `plantuml` dependency) 

Original README
===============
PlantUML is a component that allows to quickly write:

 * [Sequence diagram](http://plantuml.com/sequence-diagram),
 * [Use case diagram](http://plantuml.com/use-case-diagram),
 * [Class diagram](http://plantuml.com/class-diagram),
 * [Object diagram](http://plantuml.com/object-diagram),
 * [Activity diagram](http://plantuml.com/activity-diagram-beta) (here is [the legacy syntax](http://plantuml.com/activity-diagram-legacy)),
 * [Component diagram](http://plantuml.com/component-diagram),
 * [Deployment diagram](http://plantuml.com/deployment-diagram),
 * [State diagram](http://plantuml.com/state-diagram),
 * [Timing diagram](http://plantuml.com/timing-diagram).
 
The following non-UML diagrams are also supported:
 * [JSON data](http://plantuml.com/json)
 * [YAML data](http://plantuml.com/yaml)
 * [Network diagram (nwdiag)](http://plantuml.com/nwdiag)
 * [Wireframe graphical interface or UI mockups (salt)](http://plantuml.com/salt)
 * [Archimate diagram](http://plantuml.com/archimate-diagram)
 * [Specification and Description Language (SDL)](http://plantuml.com/activity-diagram-beta#sdl)
 * [Ditaa diagram](http://plantuml.com/ditaa)
 * [Gantt diagram](http://plantuml.com/gantt-diagram)
 * [MindMap diagram](http://plantuml.com/mindmap-diagram)
 * [Work Breakdown Structure diagram (WBS)](http://plantuml.com/wbs-diagram)
 * [Mathematic with AsciiMath or JLaTeXMath notation](http://plantuml.com/ascii-math)
 * [Entity Relationship diagram (IE/ER)](http://plantuml.com/ie-diagram)

Furthermore:
 * [Hyperlinks and tooltips](http://plantuml.com/link)
 * [Creole](http://plantuml.com/creole): rich text, emoticons, unicode, icons
 * [OpenIconic icons](http://plantuml.com/openiconic)
 * [Sprite icons](http://plantuml.com/sprite)
 * [AsciiMath mathematical expressions](http://plantuml.com/ascii-math)

To know more about PlantUML, please visit http://plantuml.com/
