=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

Objective:
This is our group's final project for our Modeling, Analysis, and Design class in Fall 2019,
during our Junior year. The objective of the design challenge was to connect human input to reliably control the movement of biomechatronic prosthetic using different sensor types. This GitHub includes the code that we used to analyze multi-sensor input and control the prosthetic. 

Given our group's love for Harry Potter, we translated the challenge as a wizard casting a spell while interfacing with sensors. The sensor data would detect what spell was cast, and if cast correctly, replicate the spell with a lego Harry’s cockroach wand and effect lego Voldemort appropriately.

System overview:
The first afferent is the push button, which is read as a digital input. The user presses it when they would like to begin their spell. If it is pressed, it is read as 1 and begins the myo data stream for 5 seconds, which is how long we determined is necessary for a spell. 

The second afferent is EMG signals. This was used to determine if the user was gripping the wand tightly enough. The integral of the absolute value over 5 seconds was taken, and through testing we determined that if it was greater than 60, the wand was indeed gripped tightly enough to cast a spell. As we learned from Hermione, proper spell-casting form is important! A spell will not be cast without it. 

If the wand was indeed gripped tightly enough to cast a spell, then the acceleration was checked to determine which spell was cast. The magnitude in the x, y, and z directions was averaged, and through testing, we determined that if it was greater than 2.9, the spell was avada kedavra, if it was in between 1.1 and 2.9, it was tarantallegra, and if less than 1.1, then no spell. This caused the motors and wand to move and cast the correct spell. 


Physical Setup:
Our circuit included 2 motors, electrodes for the leg, the push button input, 2 my daqs, and 2 power sources. Our stage, made of Legos, included harry potter and voldemort, each with their own leg mimicking their wand, with optional character faces, and a blue stick that served as the killing device. We also had a "real" Harry Potter wand that we obtained from Harry Potter World in California. 

