# Interview Test App
<br />

# Architecture
The project is implemented with MVVM and it's written fully with Kotlin. I just used one Java class for optimizing my recyclerview.

# Technologies And Decisions
In this section, I'll try to mention some of the important things I used within this project and I'll explain the reason behind some of my decisions.

## DI
I used hilt because of fewer boilerplate codes. Also generally, I think it's easier for integration tests as well.

## Coroutine and Flow
For the threading and observing, I could use technologies like Livedata with Threadpools, Rx, But I used Coroutine and Flow in this project which is my preferred way.
I can explain Why? It's hard to handle things like backpressure or debounce with Livedatas and ThreadPools.
On the other hand, Coroutine is lighter than Rx. It's native and somehow it's easier to test because Google has created some libraries for that.
*PS: I also used some codes from Google samples.*

## Data Persistence
My first approach here was Offline first, but because I wanted to reduce the complexity in this test project I decided to fetch data from the server every time it is needed, but for storing and accessing user's favorite gifs I made a local database with the help of Room library. I wrote some codes with offline-first approach and I pushed them on another branch (*offline-first branch*). I'm warning you that the branch doesn't work properly and just some of the features had been implemented there but It can give you the idea of how my offline approach would work.

## Performance optimization
The Giphy API supports pagination, so as the final step I added pagination in my project.

## UX
I kept UI minimal and tried to just show I know how to use things like constraintlayout, etc, but I guess from the user side it's not good at all :)

## Test
Today is the deadline of this project and due to the pressure of my interviews I hadn't enough time to write tests for this project. Writing tests for this task was optional, but I have this missing part in my mind and I will write some tests for the people who may like to use my codes.
