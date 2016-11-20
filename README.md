# tloll

tloll or The Legend of Ling Ling is a game play space for Team Bamboo to try concepts and different ideas. 
Ideally these would form into some fun game to both develop and play. 

IntelliJ Gradle Tips:
	- Adding the JDK can be annoying simply ebcause you can get stuck in a chicken or the egg scenario for the SDK.
	  It can configured under File > Project Structure > Project Settings > Project
	- Getting nice gradle builds can also be a bit annoying for a new user. 
	  Go to Run > Edit Configurations > Add (Green +) > Groovy
	  	- Make 3 configurations named (my suggestion): build, run, and "build & run"
			- Script Path: /home/user/dev/git/tloll/build.gradle (or wherever yours is)
			- Module: tloll
			- Script parameters: build
			- Working directory: /home/user/dev/git/tloll (or wherever yours is)
