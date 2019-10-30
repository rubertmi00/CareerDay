# Career Day Sorter
A modular sorter for students in Career Day. Can be used for a large variety of functions/events.

The schedule is generated as follows:
1. Sessions are ranked by popularity, and more popular Sessions are assigned to larger Classrooms.
2. Students who have submitted requests are ranked by their Priority.
    - Priority is determined by the time they submitted their requests, their grade level, and, in later rounds, their    
      Contentedness.
    - Contentedness is a value resulting from a calculation which compares the Student's requests with what Sessions
    they've been assigned to thus far, so that if they miss out on one of their top choices during the first period,
    they will be slightly more likely to get it later in the day.
3. Students are placed, in rank order, into their highest choice Session which still has seats available, and which they 
   haven't already seen.
4. Students who did not submit any requests for Career Day are then randomly placed in Sessions which have not been filled 
   above a decided-upon minimum capacity.
5. If, at this point, every Student in the school has been assigned all their Sessions, and there are still Sessions below their minimum capacity, Students with the lowest Priority are then reassigned from their current Session into one that is below minimum capacity.

Overall Avg. Student Contentedness varied between 90%-95% across multiple different sample inputs.

<h1>An app by Hills ATCS</h1>
<h2>Credits</h2>

* Admin Team
    - Mr. Uhl
    - Tom Varano
	
* Logic Team
    - Michael Ruberto
    - Bennett Bierman
    - Josh Kent
	
* Information Team
    - Liam Landau
    - Michael Reineke
    - Ryan Hudson
	
* Email Team
    - Sam Eichner
	
* UI Team
    - Jarrett Bierman
    - Edward Fomnykh
    - David Descherer
