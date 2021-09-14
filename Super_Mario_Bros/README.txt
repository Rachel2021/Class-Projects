=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

Growing up, I loved playing Super Mario Bros with my siblings. When tasked with creating a final
project for my intro coding class in Spring 2019, at the end of my Sophomore year, I immediately
decided to try and re-create some of the core functionality of one of my favorite childhood games!
Below are the questions we were required to answer regarding our game design. 

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections
  
I use sets to store the clouds and the Goombas of the game. 

For the clouds, I want to store them in some sort of collection as opposed to as individual 
objects because I want to know all the clouds that are on the screen at one time point and 
react appropriately. It’s also easier to iterate through them. I’m not using an array because I 
want it to be resizable, I’m not using a map because I don’t have two pieces of data I need stored 
together, and I’m not using a list because I don’t need the clouds ordered in any way. 

For the goombas, I’m using a set for similar reasons as above, especially since there are a lot 
of goombas at once. 
  

  2. File I/O
  
  I use File I/O to keep track of and display, read and write high scores.

  3. Inheritance/Subtyping for Dynamic Dispatch
  
  I use subtyping for the Mario class and the Goomba class, both of which will extend an abstract 
  character class. They will share a lot of methods, with the main differences being how they are 
  drawn, how they die, and how they move. 

  4. Testable Component
  
  I use tests to test many components of my game. In particular, I will test kill movements, since 
  there are a lot of possible intersections between Mario and the Goombas that I can’t emulate by 
  hand while playing with the GUI (I will also use it to test I/O and the collections I store the 
  clouds and Goombas in and other methods as well, since there are other aspects of my game that 
  can be tested independently of the GUI). 


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

1. Cloud

This creates a new object called "Cloud", which is a cloud moving across the screen. It stores
its position, and is drawn from a cloud image. It has getters and setters for 
all its dimensions/positions, and has a move method. This class also implements comparable, since
it will be used in a TreeSet later. The cloud object does not store velocity since the Cloud object
itself does not have a move method. Rather, the cloud set created later will have a move method.
It draws itself either as the whole image or a subimage of cloud image so that when the screen 
scrolls, the cloud can move off the screen smoothly. 

2. CloudsSet

This creates a new object called "CloudsSet" which is a TreeSet of clouds. It moves the clouds on
the screen to the left as Mario moves to the right to give the effect of scrolling. It also 
monitors how many clouds are on the screen at a given moment, and always ensures that 2 clouds
are on the screen. 

3. Game

This is the Game class. When it is first run, it prompts the user for a nickname with which to play 
with and keep track of scores. If the nickname entered is already taken by someone else in the 
scores file, it will prompt them for another. It then prompts the user for their age, an additional
piece of information stored with the scores. It will check once if the number entered is in invalid
format, and if so, prompt again. Then, it will run the game. 

3. GameCourt

This is where most of the real game handling happens. It handles all key presses and updates 
the characters accordingly. The GameCourt has a Mario, a TreeSet f Goombas, and a CloudsSet. 
It also keeps track of the user's nickname, their age, and the status of their game (if the 
game ended due to mario's death/win or if they're still playing). It also keeps track of 
the player's score and displays it. Depending on whether or not the score file was able to 
be read in correctly, when the game ends, it will either display just the user's score, or the 
user's score along with up to 3 high scores (which may or may not include the user's score). 

5. GameObj

This is the abstract super class for Mario and Goomba. It generally represents a character in the
game, so it has getters and setters for positions/dimensions/velocities. It's abstract methods
are draw, move, and killed. These are abstract since the characters are drawn differently, 
they move differently (mario moves based on the velocity set by key press set by the user), while
the Goombas move by random back and forth motion, and they are killed in different ways. 

6. Goomba

This is one of two subclasses of GameObj. It also stores "switchPx", which is used as a turn-around
point for Goomba movement. It also stores whether or not the Goomba is alive. 

7. Mario

This is one of two subclasses of GameObj. It also stores two booleans that are used to represent
one of three scenarios: Mario is currently jumping up, Mario is on his way down from a jump, or 
neither. Accordingly, there are also getters and setters for these fields, and a jump method. 

9. Score

This is the score object. Each score holds 3 pieces of information: the score, nickname, and age. 
This class also implements Comparable as I later will call Collections.sort on it. 

9. ScoreKeeper

This handles the functionality for File I/O and scorekeeping. A ScoreKeeper stores an ArrayList
of scores. It reads in a score file which must be formatted as the following: "score, nickname, age"
on each line, and creates the ArrayList of scores from it. It also has methods to check if a 
nickname is already taken (which is used by the Game class), to add a score (used by GameCourt upon
game ending), to get the top scores (used by GameCourt upon game ending), and to write new set 
of scores to a file (used by GameCourt upon game ending). 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
I struggled with deciding how to implement scrolling. I went back and forth between having an 
entirely set out level with goombas, clouds, etc. already in place so Mario's movement would 
just shift the entire level, and continuously adding goombas/clouds as opposed to having a 
pre-set level. I ended up deciding on a mix of the two. For the clouds, I used the latter. For
the Goombas, I used the former. 
 
I also really struggled with adding music to the game. Towards the end, once I had completed
my main functionality, I wanted to add background music while the game is playing since in my mind,
the Super Mario Bros theme song is quite iconic and the game isn't quite the same without it.
After researching several different libraries, I settled on sun.adio, but after several hours I 
discovered that the problem was Eclipse's restriction settings wasn't letting me use the library. 
So, I ended up not implementing music. 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think my separation of functionality is good, but it could be better. My scores and GameCourt
fields are pretty well encapsulated. If I could refactor, I would try to remove unnecesssary 
fields from my objects, since I feel as though there are some that aren't as important could be 
represented in other ways. I would also see if there was a better way to represent CloudsSet
other than the way I did - such as changing it so that the entire game is on a set track as 
opposed to just the Goombas. 
Also, I would want to make the collisions better. CUrrently, due to Image IO's bounding boxes,
when Mario is sometimes killed by a Goomba, it doesn't visually look like they're even touching. 
So, I would want to work on some more advanced collisions to fix that. 
Lastly, the scrolling doesn't quite work when Mario moves backwards, so I would like to either make 
him unable to move backwards, make the scrolling work backwards, or make it like the original game
where he can move backwards but the game doesn't scroll back with him. 

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  Mario image from Google images: https://images.app.goo.gl/nqnaxqY6vHumdLGG6
  Goomba image from Google images: https://images.app.goo.gl/jMYduGfs34ba6ZBP7
  Cloud image from Google images: https://images.app.goo.gl/EUcevD8HoLPSZrEp7
