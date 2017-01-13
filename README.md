Stocks
======

Experimental Android app with MVVM architecture. Purpose of this project is to explore different architectural approaches and also "new age" technologies like Android Data Binding, RxJava, Lambdas, Retrofit, Dagger etc.

Main goals of this project
--------------------------

* Create a good architecture with separate UI and business logic
* Write well readable, maintainable and testable code
* Follow OOP principles, SOLID principles, DRY
* Avoid God classes (no way to have a class with 500+ lines)

This project uses
-----------------

* MVVM architecture
* Data binding
* Binding adapters
* RecyclerView
* ConstraintLayout
* RxJava
* Retrolambda
* OkHttp
* Retrofit
* GSON
* Glide
* LeakCanary

This app also uses [Alfonz](https://github.com/petrnohejl/Alfonz) - multi purpose library which I have developed to make a development process easier and avoid boilerplate code.

MVVM architecture
-----------------

Project is based on MVVM architecture and uses [AndroidViewModel](https://github.com/inloop/AndroidViewModel) library. I had a talk about my MVVM approach at STRV Android Meetup. You can check a [video record](https://www.youtube.com/watch?v=vnBmdKkMLZw) or [slides](https://speakerdeck.com/petrnohejl/mvvm-architecture-on-android) for more info.

TODO
----

* 2 way binding
* Dagger
* Tests
* Proguard
* Travis

REST API
--------

* [http://dev.markitondemand.com/MODApis/](http://dev.markitondemand.com/MODApis/)

Examples:

* [http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=INTC](http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=INTC)
* [http://dev.markitondemand.com/MODApis/Api/v2/Lookup/json?input=Intel](http://dev.markitondemand.com/MODApis/Api/v2/Lookup/json?input=Intel)

Developed by
------------

[Petr Nohejl](http://petrnohejl.cz)

License
-------

    Copyright 2016 Petr Nohejl

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
