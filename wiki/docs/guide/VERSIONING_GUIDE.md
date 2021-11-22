# SEMANTIC APP VERSIONING

In the world of software management there exists a dreaded place called **“dependency hell.”** The bigger your system grows and the more packages you integrate into your software, the more likely you are to find yourself, one day, in this pit of despair. 

**Dependency hell** is where you are when version lock and/or version promiscuity prevent you from easily and safely moving your project forward. 

As a solution to this problem, we propose a simple set of rules and requirements that dictate how version numbers are assigned and incremented. 

We call this system **“Semantic Versioning.”** Under this scheme, version numbers and the way they change convey meaning about the underlying code and what has been modified from one version to the next. 

Given a version number MAJOR.MINOR.PATCH, increment the: 

* **MAJOR** version when you make incompatible API changes, 
* **MINOR** version when you add functionality in a backwards compatible manner, and 
* **PATCH** version when you make backwards compatible bug fixes. 
* **BUILDTYPE** are supported build types.  
* **FLAVOR**  disabled (0) 
* **BUILDNUMBER** is the number that denotes the number of builds you have performed 
* Additional labels for pre-release and build metadata are available as extensions to the **MAJOR.MINOR.PATCH.BUILDTYPE.FLAVOR.BUILDNUMBER format. **

**Example:** 1.0.0-alpha < 1.0.0-alpha.1 < 1.0.0-alpha.beta < 1.0.0-beta < 1.0.0-beta.2 < 1.0.0-beta.11 < 1.0.0-rc.1 < 1.0.0. 

     ```   /**
         * Here is representation of Version code generation from version name.
         *
         *  *---------- major version
         *  |  *------- minor version
         *  |  |  *---- patch version
         *  |  |  |*--- buildType (dev/alpha/beta/rc/release)
         *  |  |  ||*-- flavor - disabled.
         *  |  |  |||*- buildNumber revision
         *  X00X00XXX00
         * so
         *  1.13.20-alpha-v19 = 10130201
         * see details below:
         *  *---------- 1 is major version
         *  |  *------- 13 is minor version
         *  |  |  *---- 20 is patch version
         *  |  |  |*--- type 1 is alpha.
         *  |  |  ||*-- flavor 0 (disabled)
         *  |  |  |||*- buildNumber 19
         *  10130201019
         */
     ```   
     
**Reference** 
------------------
* https://semver.org/
* https://gist.github.com/dekalo-stanislav/9ad5f76cc2b49828acbf0634f6586b6c
