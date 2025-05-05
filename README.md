# ✈️ Shortest-Path-Airport-Navigator
My CS2400 Data Structures and Advanced Programming final project. 
This program allows users to query airport information and find the shortest path between airports using Dijkstra's algorithm.
Designed to demonstrate real-world applications of various types of data structures.

# 📚 Data Structures Used
- **Directed Graph** – models one-way flight paths between airports
- **Vertex** – represents each airport node in the graph
- **Hashed Dictionary** – stores adjacency lists for quick airport lookup
- **Priority Queue (Max-Heap)** – selects the next airport with the shortest known distance
- **Stack** – used in path reconstruction

# 📦 Getting Started
1. **Clone the repo:**
```bash
git clone https://github.com/maranan8/Shortest-Path-Airport-Navigator.git
cd Shortest-Path-Airport-Navigator
```

2. **Compile and Run:**
```bash
javac AirportApp.java
java AirportApp
```
3. **Enter Options**
- Enter Q to query the airport information by entering the airport code
- Enter D to find the minimum distance between two airports
- Enter H for help
- Enter E to exit the program

## 📸 Screenshot
<img width="744" alt="Screenshot 2025-05-04 at 7 57 53 PM" src="https://github.com/user-attachments/assets/3b8b1d0e-1531-4f83-8947-b8e8dd5bf72c" />
