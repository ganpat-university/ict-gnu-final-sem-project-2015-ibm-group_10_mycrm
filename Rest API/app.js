/*eslint-env node*/

//------------------------------------------------------------------------------
// node.js starter application for Bluemix
//------------------------------------------------------------------------------

// This application uses express as its web server
// for more info, see: http://expressjs.com
var express = require('express');

// cfenv provides access to your Cloud Foundry environment
// for more info, see: https://www.npmjs.com/package/cfenv
var cfenv = require('cfenv');

// create a new express server
var app = express();

// serve the files out of ./public as our main files
app.use(express.static(__dirname + '/public'));

// get the app environment from Cloud Foundry
var appEnv = cfenv.getAppEnv();
/**
Packages and Authentications
**/
var http = require("http");
var bodyParser = require('body-parser');
var urlencodedParser = bodyParser.urlencoded({ extended: true });
var md5 = require("md5");

var admin = require("firebase-admin");
var serviceAccount = require("./mycrm-d955f.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://mycrm-d955f.firebaseio.com"
});

var db = admin.firestore();
var db = admin.firestore();
var docRef = db.collection('contacts');
var userRef = db.collection('users');
var noteRef = db.collection('notes');

var contactCount = 19;
var notesCount = 4;

/**
LIST USERS API
**/

app.get('/listUsers',/* @callback */ function (req, res) {
	
	if(!req.query.contact){
		console.log('No Data');
		res.status(404).send('No Data');
	}
	else{
		var d1 = req.query.contact;
		var contactRef = docRef.doc(d1);
		var data = {};
		var getDoc = contactRef.get()
		.then(doc => {
			if (!doc.exists) {
			var e1 = "No such Document :: " +d1;
			res.status(404).send(e1);
			} else {
				console.log(doc.data());
				data[doc.id] = doc.data();
				res.send(data);
			}
		})
		.catch(err => {
			console.log('Error getting document', err);
			res.status(500).send('Error getting document');
		}); 
	}
});


/** CREATE USER **/
app.post('/createContact', urlencodedParser, function (req, res){
	
  var c1 = null;
  if(req.body.contactno){
	  c1 = req.body.contactno;
  }
  if(!req.body.contactno){
	c1 =  contactCount.toString();
	contactCount = contactCount + 1;
  }
  var tstamp = Date.now();
  
  var setAda = {
	  address: req.body.address,
		available: req.body.avl,
		bio: req.body.bio,
		designation: req.body.designation,
		dob: req.body.dob,
		email: req.body.email,
		name: req.body.name,
		organization: req.body.org,
		password: md5(req.body.password),
		stamp: tstamp,
		phone: req.body.mobile,
		phone2: req.body.landline,
		location: {"_latitude":req.body.latitude,"_longitude":req.body.longitude}
  };
  docRef.doc(c1).set(setAda);
	console.log(contactCount.toString() + " is added");
	res.send(setAda);
 });

 /** Login USER **/
 app.post('/authUser', urlencodedParser, function (req, res){
var user = req.body.user;
	//var pass = req.body.pass;
	var pass = md5(req.body.pass);
	
	if(user == '' || user == null || pass == '' || pass == null){
		console.log('No Data');
		res.status(404).send('No Data');
	}
	else{
		var queryRef = docRef.where('email','==',user).where('password','==',pass).get();
		if(!queryRef){
			res.send('no data');
		}
		var searchData = {};
		queryRef.then((snapshot) => {
			snapshot.forEach((doc) => {
			console.log(doc.id, '=>', doc.data());
			searchData[doc.id] = doc.data();
			});
		res.send(searchData);
		})
		.catch((err) => {
			console.log('Error getting documents', err);
		});
	}
 });
 
 /** List all Contacts **/
 app.get('/listContacts',/* @callback */ function (req, res) {
	
	//var d1 = req.query.contact;
	var data = {};
   var contactRef = docRef.get()
	.then((snapshot) => {
    snapshot.forEach((doc) => {
		data[doc.id] = doc.data();
      console.log(doc.id, '=>', doc.data());
    });
	res.send(data);
  })
  .catch(err => {
    console.log('Error getting document', err);
	res.status(500).send('Error getting document');
  }); 
});

 /** SEARCH **/
app.post('/search', urlencodedParser, function (req, res){

// Create a query against the collection
//var dbfield1 = req.body.field1;
var designation = req.body.designation;
var organization = req.body.organization;
var address = req.body.address;
var queryRef = null;

if(designation && organization && address){
	queryRef = docRef.where('designation','==',designation).where('organization','==',organization).where('address','==',address).where('available','==',"true").get();
}
else if(designation && organization){
	queryRef = docRef.where('designation','==',designation).where('organization','==',organization).where('available','==',"true").get();
}
else if(designation && address){
	queryRef = docRef.where('designation','==',designation).where('address','==',address).where('available','==',"true").get();
}
else if(organization && address){
	queryRef = docRef.where('organization','==',organization).where('address','==',address).where('available','==',"true").get();
}
else if(designation){
	queryRef = docRef.where('designation','==',designation).where('available','==',"true").get();
}
else if(organization){
	queryRef = docRef.where('organization','==',organization).where('available','==',"true").get();
}
else if(address){
	queryRef = docRef.where('address','==',address).where('available','==',"true").get();
}
else{
	queryRef = docRef.where('available','==',"true").get();
}
	var searchData = {};
queryRef.then((snapshot) => {
    snapshot.forEach((doc) => {
		var x = doc.id;
		searchData[x] = doc.data();
      console.log(x, '=>', doc.data());
    });
	res.send(searchData);
  })
  .catch((err) => {
    console.log('Error getting documents', err);
  });
});


/* Read Note */
app.post('/readNotes', urlencodedParser, function (req, res){
	var email = req.body.email;
	var data = {};
	//Read all Notes
	noteRef.doc(email).collection('1').get()
	.then((snapshot) => {
    snapshot.forEach((doc) => {
		data[doc.id] = doc.data();
		console.log(doc.id, '=>', doc.data());
		});
		res.send(data);
	})
	.catch((err) => {
    console.log('Error getting documents', err);
	});
	
 });
 
 /* CREATE NOTE */
app.post('/createNotes', urlencodedParser, function (req, res){
	var n1 = null;
	if(req.body.noteid){
		n1 = req.body.noteid;
	}
	if(!req.body.noteid){
		n1 = notesCount.toString();
		notesCount = notesCount + 1;
	}
	var email = req.body.email;
	var note = req.body.note;
	var tstamp = Date.now();

	var setAda = noteRef.doc(email).collection('1').doc(n1).set({
		tamp: tstamp,
		note: note,
	});
	console.log(notesCount.toString() + "is added");
	res.send(setAda);
 });

/** Delete Contact **/
 app.post('/deleteContact', urlencodedParser, function (req, res){

	var cid = null;
	if(!req.body.contact){
		res.status(404).send('No Data');
	}
	else{
		cid = req.body.contact;
		var queryRef = docRef.doc(cid).delete();
		console.log(queryRef);
		res.send(queryRef);
	}
 });
 
 /** Delete Note **/
 app.post('/deleteNote', urlencodedParser, function (req, res){

	if(!req.body.noteid || !req.body.user){
		res.status(404).send('No Data');
	}
	else{
		var nid = req.body.noteid;
		var user = req.body.user;
		var queryRef = noteRef.doc(user).collection('1').doc(nid).delete();
		console.log(queryRef);
		res.send(queryRef);
	}
 });


// start server on the specified port and binding host
app.listen(appEnv.port, '0.0.0.0', function() {
	
  // print a message when the server starts listening
  console.log("server starting on " + appEnv.url);
});
