// db.bikes.insertMany(
//     Array.from({ length: 10000 }).map((_, index) => ({
//         name: ["Specialized", "Giant", "Trek", "Scott", "BMC", "Santa Cruz", "Norco", "Cube",][index % 8],
//         type: ["MTB", "Downhill", "Freeride", "Gravel"][index % 4],
//         pricePerHour: (index % 20) + 21,
//         owner: [ "John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "martin",][index % 7], index: index,
//     }))
//     );
//
// db.users.insertMany(
//     Array.from({ length: 10000 }).map((_, index) => ({
//         name: ["John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "Martin"][Math.floor(Math.random() * 7)],
//         surname: ["Smith", "Johnson", "Brown", "Wilson", "Lee", "Davis", "Evans"][Math.floor(Math.random() * 7)],
//         phonenumber: ["555-1234", "555-5678", "555-9876", "555-4321", "555-8765"][Math.floor(Math.random() * 5)],
//         email: `email${index}@${["example.com", "gmail.com", "yahoo.com", "hotmail.com"][Math.floor(Math.random() * 4)]}`
//     }))
//     );
//
// db.rents.insertMany(
//     Array.from({length: 10000}).map((_, index) => ({
//         day: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"][index % 7],
//         bike: db.bikes.find({}, { _id: 1 }).limit(20).toArray().map(doc => doc._id)[index % 20],
//         customer: db.users.find({}, { _id: 1 }).limit(20).toArray().map(doc => doc._id)[index % 20]
//     }))
//     );

db.bikes.insertMany(
    Array.from({ length: 10000 }).map((_, index) => ({
        name: ["Specialized", "Giant", "Trek", "Scott", "BMC", "Santa Cruz", "Norco", "Cube",][index % 8],
        type: ["MTB", "Downhill", "Freeride", "Gravel"][index % 4],
        pricePerHour: (index % 20) + 21,
        owner: [ "John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "martin",][index % 7],
        index: index
    }))
);

db.users.insertMany(
    Array.from({ length: 10000 }).map((_, index) => ({
        name: ["John", "Mike", "Tom", "Jack", "Thomas", "Jonny", "Martin"][Math.floor(Math.random() * 7)],
        surname: ["Smith", "Johnson", "Brown", "Wilson", "Lee", "Davis", "Evans"][Math.floor(Math.random() * 7)],
        phonenumber: ["555-1234", "555-5678", "555-9876", "555-4321", "555-8765"][Math.floor(Math.random() * 5)],
        email: `email${index}@${["example.com", "gmail.com", "yahoo.com", "hotmail.com"][Math.floor(Math.random() * 4)]}`,
        index: index
    }))
);

db.rents.insertMany(
    Array.from({length: 10000}).map((_, index) => ({
        day: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"][index % 7],
        bike: db.bikes.find({}, {}).limit(20).toArray()[index % 20],
        customer: db.users.find({}, {}).limit(20).toArray()[index % 20],
        index: index
    }))
);


db.bikes.find({name: "Giant"}).explain("executionStats")
db.users.find({name: "Jack"}).sort({name: 1})
db.rents.deleteMany({})
db.bikes.deleteMany({})
db.users.deleteMany({})
db.bikes.deleteMany({type: "MTB"})
db.bikes.dropIndexes()
db.bikes.insertOne({name: "Trek", type: "MTB", owner: "Mike", pricePerHour: 44})
db.bikes.createIndex({name:1})
db.bikes.createIndex({index:1})
db.rents.createIndex({index:1})
db.users.createIndex({index:1})

db.bikes.aggregate([
    {
        $match: {
            type: "Gravel",
            pricePerHour: { $gte: 30 }
        }
    },
    {
        $group: {
            _id: null,
            count: { $sum: 1 }
        }
    }
])