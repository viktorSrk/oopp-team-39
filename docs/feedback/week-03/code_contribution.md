# Code Contributions and Code Reviews

#### Focused Commits

Feedback: Good, Most commit messages are concise one liners, which clearly summarize the change.

Make sure the commits aren't too big. For example for cards, it's better to split the commits up. By for example first creating the class and it's tests and afterwards commit the backend functionality for it.
You could even split up the commits into creating class and creating tests.


#### Isolation

Feedback: Very Good, feature branches/merge requests have been used to isolate individual features during development.

#### Reviewability

Feedback: Very good, the MR's have a clear focus that becomes clear from the title and the description.

The changes aren't too big. As in they don't deviate too much from main and in your case each MR covers a small amount of commits (is allowed to be more).

#### Code Reviews

Feedback: Very good, The MRs so far have good discussion that go back and forth. Keep this up.

The comments are constructive and goal orientated and lead to improvements of the code.

#### Build Server

Feedback: Good, Builds do not fail too frequently, esp.(!) on the main branch and don't take too long.

Make sure to add atleast 10+ checkstyle rules to your repository and check your checkstyles before commiting.