# Assignment 1: Research about Normalization

**Normalization** is the process of organizing data in a database to reduce redundancy and improve data integrity. 

> 🎯 Separate data into different tables and define relationships between them to eliminate data anomalies such as insertion, update, and deletion anomalies.

Normalization typically involves applying a series of rules called normal forms (NF) to ensure the database structure is optimized. Here are the explanations and examples of the first three normal forms (1NF, 2NF, and 3NF).

## First Normal Form (1NF)

A table is in the first normal form if:
- All columns contain atomic (indivisible) values.
- Each column contains values of a single type.
- Each column contains unique values within a row (no duplicate columns).

**Example:**

Consider a table `Order`:

| OrderID | CustomerName | Products          |
|---------|--------------|-------------------|
| 1       | Alice        | Apple, Banana     |
| 2       | Bob          | Orange            |
| 3       | Alice        | Banana, Grape     |

This table is not in 1NF because the `Products` column contains multiple values.

To convert it to 1NF, we need to ensure that each column contains atomic values:

| OrderID | CustomerName | Product  |
|---------|--------------|----------|
| 1       | Alice        | Apple    |
| 1       | Alice        | Banana   |
| 2       | Bob          | Orange   |
| 3       | Alice        | Banana   |
| 3       | Alice        | Grape    |

## Second Normal Form (2NF)

A table is in the second normal form if:
- It is in 1NF.
- All non-key columns are fully dependent on the primary key.

**Example:**

Consider the 1NF table `Order`:

| OrderID | CustomerName | Product  |
|---------|--------------|----------|
| 1       | Alice        | Apple    |
| 1       | Alice        | Banana   |
| 2       | Bob          | Orange   |
| 3       | Alice        | Banana   |
| 3       | Alice        | Grape    |

This table is not in 2NF because the `CustomerName` column is dependent on `OrderID`, but not on `Product`.

To convert it to 2NF, we decompose it into two tables:

**Customers Table:**

| OrderID | CustomerName |
|---------|--------------|
| 1       | Alice        |
| 2       | Bob          |
| 3       | Alice        |

**OrderDetails Table:**

| OrderID | Product  |
|---------|----------|
| 1       | Apple    |
| 1       | Banana   |
| 2       | Orange   |
| 3       | Banana   |
| 3       | Grape    |

## Third Normal Form (3NF)

A table is in the third normal form if:
- It is in 2NF.
- All non-key columns are not transitively dependent on the primary key.

**Example:**

Consider the 2NF tables `Customers` and `OrderDetails`:

**Customers Table:**

| OrderID | CustomerName | CustomerAddress  |
|---------|--------------|------------------|
| 1       | Alice        | 123 Apple St     |
| 2       | Bob          | 456 Orange Ave   |
| 3       | Alice        | 123 Apple St     |

**OrderDetails Table:**

| OrderID | Product  |
|---------|----------|
| 1       | Apple    |
| 1       | Banana   |
| 2       | Orange   |
| 3       | Banana   |
| 3       | Grape    |

The `CustomerAddress` column in the `Customers` table is transitively dependent on `OrderID` via `CustomerName`.

To convert it to 3NF, we further decompose the `Customers` table:

**Customers Table:**

| CustomerID | CustomerName |
|------------|--------------|
| 1          | Alice        |
| 2          | Bob          |

**Orders Table:**

| OrderID | CustomerID |
|---------|------------|
| 1       | 1          |
| 2       | 2          |
| 3       | 1          |

**CustomerAddresses Table:**

| CustomerID | CustomerAddress  |
|------------|------------------|
| 1          | 123 Apple St     |
| 2          | 456 Orange Ave   |

By doing this, we ensure that all non-key columns are fully dependent on the primary key, and there are no transitive dependencies.

Normalization helps in maintaining the consistency and integrity of the data within a relational database by organizing data into related tables and eliminating redundancy.
