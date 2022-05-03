const express = require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient
const url = "mongodb+srv://naimon:qLDJXFdyTKhDxldR@cluster0.8im00.mongodb.net/test?retryWrites=true&w=majority"
app.use(express.json())

mongoClient.connect(url, (err, db) => {

    if (err) {
        console.log("Nie udalo sie polaczyc z MongoDB")
    } else {

        const myDb = db.db('test')
        const collection = myDb.collection('myTable')

        app.post('/signup', (req, res) => {

            const newUser = {
                nazwa_uzyt: req.body.nazwa_uzyt,
                imie: req.body.imie,
		nazwisko: req.body.nazwisko,
		email: req.body.email,
                password: req.body.password,
		konto: req.body.konto,
		adres: req.body.adres,
		telefon: req.body.telefon
            }

            const query = { $or: [ {email: newUser.email}, {nazwa_uzyt: newUser.nazwa_uzyt} ]}

            collection.findOne(query, (err, result) => {

                if (result == null) {
                    collection.insertOne(newUser, (err, result) => {
                        res.status(200).send()
                    })
                } else {
                    res.status(400).send()
                }

            })

        })
	app.post('/wyszukaj', (req, res) => {
	    collection.find({"konto": "Doktor"}).toArray(function(err, result) {
 	    if (err) throw err;
            res.status(200).send(JSON.stringify(result))
	    });
 	})

	app.post('/pacjenci', (req, res) => {
	    collection.find({"konto": "Pacjent"}).toArray(function(err, result) {
 	    if (err) throw err;
            res.status(200).send(JSON.stringify(result))
	    });
 	})


	 app.post('/profil', (req, res) => {

            const query = {
                nazwa_uzyt: req.body.nazwa_uzyt
            }

            collection.findOne(query, (err, result) => {

                if (result != null) {

                    const objToSend = {
                        nazwa_uzyt: result.nazwa_uzyt,
                        email: result.email,
			konto: result.konto,
			imie: result.imie,
			nazwisko: result.nazwisko,
			adres: result.adres,
			telefon: result.telefon
                    }

                    res.status(200).send(JSON.stringify(objToSend))

                } else {
                    res.status(404).send()
                }

            })

        })

        app.post('/login', (req, res) => {

            const query = {
                email: req.body.email, 
                password: req.body.password
            }

            collection.findOne(query, (err, result) => {

                if (result != null) {

                    const objToSend = {
                        name: result.name,
                        email: result.email,
			konto: result.konto,
			imie: result.imie,
			nazwisko: result.nazwisko,
			adres: result.adres,
			telefon: result.telefon
                    }

                    res.status(200).send(JSON.stringify(objToSend))

                } else {
                    res.status(404).send()
                }

            })

        })


    }

})

app.listen(3000, () => {
    console.log("Slucham na porcie 3000...")
})