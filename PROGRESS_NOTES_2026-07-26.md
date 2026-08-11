# Railway Project Progress — 26 July 2026

## Assignment requirements reviewed

The `CCS2300_GroupAssignment.pdf` was reviewed and compared with the project.
For the Week 10 progress video, the important data structures are:

- Array
- Linked list
- Stack
- Queue
- Binary Search Tree
- AVL tree
- Graph, BFS, and DFS
- Hash table
- Set ADT

Sorting algorithms were intentionally left outside the current scope.

## Completed before and during this session

- Fixed station array
- Custom train linked list
- Linear train search
- Custom user hash table with separate chaining
- Railway graph using an adjacency list
- BFS and DFS graph traversals
- User authentication
- Train administration
- Ticket booking and cancellation

## Stack implementation

Implemented a custom linked stack in:

- `src/structure/ActionStack.java`
- `src/model/BookingAction.java`

The stack:

- Records successful bookings
- Records booking cancellations
- Records automatic bookings from the waiting queue
- Displays each user's actions newest-first
- Supports `push`, `pop`, `peek`, `isEmpty`, and `size`

The user menu now includes `Recent actions`.

Complexity:

- Push: O(1)
- Pop: O(1)
- Peek: O(1)
- Display: O(n)

## Queue implementation

Implemented a custom linked FIFO queue in:

- `src/structure/WaitingListQueue.java`

The queue is integrated with `BookingService` and the user menu.

Current behavior:

1. A user tries to book a full train.
2. The user is placed in that train's waiting queue.
3. Duplicate waiting entries are rejected.
4. When an existing passenger cancels, the first waiting user automatically
   receives the available seat.
5. The automatic booking is recorded in the action stack.

The user menu now includes `My waiting list`.

Complexity:

- Standard enqueue: O(1)
- Standard dequeue: O(1)
- Peek: O(1)
- Find/dequeue for a particular train: O(n)

## Route-first ticket booking

Changed ticket booking so the user now:

1. Enters a start station.
2. Enters a destination.
3. Sees only trains with that exact route.
4. Selects a train ID from those results.

An unrelated train ID is rejected. Route matching is case-insensitive.
The waiting-list behavior still works for full matching trains.

Relevant files:

- `src/menu/UserMenu.java`
- `src/service/BookingService.java`
- `src/structure/TrainLinkedList.java`

Note: Each train still stores only a start station and destination. Intermediate
stops such as `A -> B -> C -> D` are not currently represented.

## Set ADT implementation

Implemented a custom hash-based Set ADT in:

- `src/structure/TrainIdSet.java`

It:

- Stores unique train IDs
- Uses separate chaining for collisions
- Handles IDs case-insensitively
- Rejects duplicate IDs
- Removes an ID when its train is deleted
- Allows a deleted ID to be reused
- Can display all buckets

The admin train menu now includes:

```text
6. Display train ID set
7. Back
```

Complexity:

- Add/contains/remove average case: O(1)
- Add/contains/remove worst case: O(n)
- Space: O(n + bucket capacity)

## Diagrams

Created two separate PlantUML files:

- `diagrams/railway-er-diagram.puml`
- `diagrams/railway-class-diagram.puml`

The ER diagram uses Chen notation:

- Rectangles for entities
- Ovals for attributes
- Underlined ovals for primary keys
- Diamonds for relationships
- 1/N labels for cardinality

The UML class diagram reflects the Java project and was updated after completing
the Set ADT.

## Validation performed

- The complete project compiles using the configured OpenJDK 25.
- A focused smoke test ran 48 successful checks across the implemented
  structures and services.
- Stack booking/cancellation order was tested.
- Waiting-queue automatic seat assignment was tested.
- Route-first booking was tested with valid and invalid train selections.
- Set duplicate rejection, deletion, display, and ID reuse were tested.

## Changes that were intentionally undone

A change that restricted new trains to the predefined station array was added
and then reverted at the user's request. Admins can currently enter free-text
start and destination values when adding a train.

## Main work remaining

1. Implement and integrate `TrainBST`.
2. Implement and integrate `TrainAVLTree`.
3. Run a final full-system demonstration.
4. Capture screenshots/testing evidence for the video.
5. Prepare a short complexity table and each member's explanation.
6. Prepare and record the 8–10 minute Week 10 progress video.

Recommended order for the next session:

```text
BST -> AVL tree -> integration testing -> screenshots -> video preparation
```

## Known design limitations

- Data is stored only in memory.
- Passwords are plain text and the admin password is hard-coded; acceptable for
  the current academic console-project scope.
- Train routes contain only start and destination, not ordered intermediate
  stops.
- Deleting a train with active bookings can leave those bookings referencing the
  removed train.
- BST and AVL classes are still empty placeholders.

