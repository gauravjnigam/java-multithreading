# java-multithreading - Sleeping barber problem

## Problem statement 
1. Small barber shop has two door, an entrance and an exit
2. M Barbers to serve customer one at a time
3. Each barber has chair to server customer for hair cut
4. barber sleeps in his chair when there is no customer in the shop waiting for their hair cut
5. Cutomer arrives at random interval 
6. when cutomer arrives and finds a barber sleeping, he awakens the barber and sit on his chair and sleep while heir is being cut.
7. if all the barber is busy cutting hair, customer goes asleep in one of the N waiting chairs.
8. when barber finish hair cut, he awakens the customer and opens the exit door. He awakens the already waiting cutomer and wait for customer to sit on chair,
otherwise goes to sleep.
9. cutomer first go to waiting room
10. barber checks the waiting room and even if their is one customer, the barber starts the hair cut.
11. After cutting the hair of cutomer, the barber will take a short time to get ready for next cutomer.


Desing consideration - 
- ensures no deadlock and starvation 
- Ensures fairness 
- efficient solutions
- Initial setup needs followings 
	- Number of barbers
	- Number of waiting chairs
	- Mean haircut time
	- Mean arrival time for cutomer
	- 
