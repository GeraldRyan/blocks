# "Beancoin" crypto! 
###(beta)

Create a new blockchain and Crypto (e.g. "Beancoin") with your friends, for your customers or for your company. Use our blockchain technology as a cryptocurrency or other form of transaction and record keeping where data is previous and veracity and publicity is a requirement. 

A blockchain is basically an open source, secure and reliable (under the right conditions) ledger, that can store data- for instance "transactions". See below for more facts. 

A blockchain is a community affair. Multiple people contribute to the validation of chains by "mining" blocks and listening on the network to node broadcasts of others. That is optional. You can simply choose to transact, using such a technology platform as this, but mining (including for reward) is enabled. 

# Simple start

Simply, go to the homepage (http://localhost:8080/blocks/). 

You will find a link to the typical Login and registration page. If you don't have an account, or if you do, you konw the drill. Here is a link to the registration page. http://localhost:8080/blocks/register. Choose a user name, secure password- you know what to do. 

Upon successful registration, you will be taken to a welcome page. You will see you will have been given a wallet of 1000 coins of the local currency. What they are worth is up to you and the community you are part of. Will they someday be worth a fortune? That is up to you to decide, and your community's popularity, what they stand for, what you do with them.

Let's go to your wallet page. http://localhost:8080/blocks/wallet/

On this page it shows your current balance and has a link for making a transaction (sending money to another). 

Your wallet is very important. It doesn't store your money. Your money exists as an idea, as a belief, as a record on the blockchain. There will be an overwhelming consensus that you own a certain balance. But your wallet contains your "private key" that allows you to make transactions (send money), as well as a public key, that allows others to verify the transactions done in your name were indeed authorized by you. Therefore keep access to your wallet safe. This is a very secure system- as long as your information is kept private. 

Your wallet is virtual and stored in our database, so all you have to do is log in, just like you would to a bank. In this way we can control security and prevent unauthorized access. Just guard your login information. If need be you can request a replacement wallet and we can transfer over the funds if you think you've been hacked. 

There's not much else you need to know to get started. It's that simple. Just. Like. A. Bank. But cooler. 

# Our offerings

Our business is offering blockchain/crypto services, for all types of customers and all needs. Example uses can be as reward tokens for customer loyalty, or for serious person to person transfers of value. We provide the infrastructure, technology consultancy and support.   

You can choose your coin name - e.g. "Beancoin" 

You choose the level of difficulty (target time to processs new blocks- see below)

How many coins will ever be in existence- or no limit. 

Who can register- anyone, or select members. 

We can also create wallets and services for existing coins, as well as tracking features of prices of popular coins (coming soon). 

BE PART OF THE FUTURE!

# NOTE ON BETA

-- this site is in dev mode. Structure, UX/UI to come. Core functionality good. 

-- Want to join our team? Connect with us here. 

## FAQ For the Curious

### How is this secure? Why can't this be hacked? What gives? 

In short, it is based on mathematics. It would take far too much computing power to overpower the collective power of the community. Mining is too difficult for individuals, even with great computing power, to overpower the overal community. The community is made up of "nodes". You need not be a mining or verifying node to partake in the transactions. In fact most don't. It doesn't take a lot to make the system secure. By listening to given channels on our network (see below && We are using PubNub as our provider), you will hear of transactions dispatched (like the money you sent your brother) as well as succesful mining of blocks. Now would be a good time to talk about "mining" and of the chain. 

### What actually is mining, what is the blockchain? 

This will sound silly but the blockchain is just a chain of blocks! Next question. 

Ok kidding. But it is though. Ok what is a block? A block is a data structure that contains key information, such as its timestamp, it's hash, it's data payload (oh that's where the good stuff is), level of difficulty, as well as the hash value of its immediate predecessor, terminating at the genesis block, which is constant for every instance (version) of this blockchain app. If the genesis block of someone's chain is not valid, then her chain is not valid. If the block's overall data doesn't hash to a certain value that matches what it claims (why people can't fake data), then the block is invalid and so is the chain. If the block is valid in itself but its last hash does not correspond to the hash of the block it is supposed to follow, the chain is invalid. If a chain of someone's instance is valid, but a longer also valid chain appears, even if they disagree on the blocks belonging in that chain, the shorter chain agrees to replace thir chain, because its no longer the truth to the community. This will be a very very rare case fwiw, and if it does happen and gets serious, that's when a 'fork" of a chain can occur, and two coins emerge from one, with people having a stake in each. Again this is rare. Now why can't SPECTRE make their own chain and claim it as truth? To do so they have to have a longer valid chainand we haven't even talked of mining difficulty. 


HERE IS AN EXAMPLE OF A SIMPLE BLOCKCHAIN WITH DUMMY DATA. 
Scroll down to see what type of structure that data portion would really include 
![image of a simple blockchain](https://imgur.com/SNUtPL5 "Simple blockchain structure.")

### What makes it so difficult to mine? (Even more advanced)

The level of difficulty is a number that can fluctuate, but the time it takes to process a block is set to a target- for example 10 minutes like blockchain. Well what if someone mines a block quicker? Simple- the system adjusts. It is dynamic and responsive. And if a block goes over the target, the difficulty adjust downward. 

Ok so how is difficulty implemented? 

It is implemented as leading zeros of a hash. For more information on hashing, there are many types and flavors but its maybe best to consult wikipedia. https://en.wikipedia.org/wiki/Hash_function. The level of difficulty n demands that n leading zeros start the hash (in binary or base 2, not base 16, so that makes it far easier). Yet at a certain point, it can get pretty difficult or time consuming, and this is ultimately independent on the number of users or computing power, as it will adjust to our target time, which can be set by the client, customized to your needs. Custom configuration is part of the game. And btw the data never changes, unless you pull a different set of transactions to "mine" (see below) but ther eare a few things that add randomness that give the ability to meet the requirements: the "nonce" (random) value you choose and the timestamp, at least. But once you hash the block to a value that meets the current difficulty, you win, you can broadcast the block, others will confirm and you'll get a reward. 

Let's take a look at hash values
Here an example of a real hash value from bitcoin. You might have heard of it. 
00000000000000000005e9992d723052a5cbb1d9b756c4d1d6e3bc403fd1d50a

Note the leading zeros. That is hard to arrive at given a blob of data. It is not accidental. It is the result of a lot of hard computer effort, and energy resources unfortunately. There is also a "proof of stake" alternative model that doesn't require as much work. In either case, there is a need for a model to prevent malicious hackers or opportunists from having an arbirary say. There has to be a sufficient barrier of entry to declaring what is the truth on the chain. This proof of work actually started way back with email, to discourage spammers, making it not worth their while, while not being a signifant cost to real folks.

There's the hash above. But note, this is hexadecimal- base 16. That makes it so much more impressive. 
Here is the same number in binary (base 2): 

0101 1110 1001 1001 1001 0010 1101 0111 0010 0011 0000 0101 0010 1010 0101 1100 1011 1011 0001 1101 1001 1011 0111 0101 0110 1100 0100 1101 0001 1101 0110 1110 0011 1011 1100 0100 0000 0011 1111 1101 0001 1101 0101 0000 1010 

Wait, that doesn't look impressive. But wait again- it turns out you most online coverters strip the leading zeros, so for each zero in hex, you have to add 4 zeroes or 19*4 or 76, plus the one already on the binary chain. That could have been happenstance. We don't know what the true level of difficulty is but we know it was under 77 and probably 76 or 75 (50:50). 

Here is a good online coverter of hex to binary. https://www.binaryhexconverter.com/hex-to-binary-converter.  

### What happens to transactions sent over the network? 

Everyone in the network is a peer. There is no central source of authority, although we are the hosts of the users and their databases. We created this app, this implementation of the blockchain, and we host users wallets and accounts on our secure databases, but nothing is stopping others from setting up their own wallets, if they have the know how. We are able to privde the private key data to its owners to do such things but the architecture of blockchain is peer to peer through and through. No one is ruler. You listen on the network, to the level you want to participate, on different channels (transaction updates on the TRANSACTIONS channel, and block updates on the BLOCK channel). On our server, which you will probably be useing, we give you the ability to mine, through us. We have our version of transactions just like anybody. They are stored in our database, as a Transaction Pool. From there when one of our users chooses to mine, they automatically select a batch of transactions and hash a block till they meet proof of work. Then they become part of the block and become removed from the database. Just as everyone has their source of the chain that syncs upregularily, they have a similar pool of transacitons. 

HERE IS AN EXAMPLE OF THE REAL 'DATA' PART OF A BLOCKCHAIN. 
This is all part of just one block, having capacity to contain thousands of individual transactions with money being directed to different wallets as signed by the the given address. 
![Block with Transaction Data](https://imgur.com/ijoGvhr", Block with Transaction Data ")


### Why can't transactions be forged? 

Great question. Because of the public/private key principle. This is done under the hood automatically by us so you don't have to worry about anything except keeping your login secure (and not overspending--https://www.ramseysolutions.com/). Like magic, one of the key pair can encrypt (or sign) and the other can decript (or verify a signature). They can actually serve either function, but one is arbitrarily chosen to be public and another private. Keep it private. And then everyone is given an address from a very very very large address space. Speaking of space- if you send to an address that no one owns- you can. Your transaction won't be rejected. These coins will be effectively lost to space, like precious jewels on the moon. In a way, this is a risk and could be a dangerous aspect of cryptocurrency, but it is also a virtue of it. There are no refunds or chargebacks--- but there are no refunds. What you do has permannce. That is both a good thing and a bad thing but the good side is it can bring other forms of security and anonymity, and there is always the traditional banking way. That will not go away. Private and public keys are very mathematical. Most people just trust they work but ther eis much reading you could do. Maybe you are the next Elon. or Satoshi. 

### Why "beancoin"?

Our stack was developed in Java, which has the concept of "java beans or enterprise beans- a form of programming 'objects', and we also love coffee, and it sounds like bitcoin- hence, beancoin". For your coin you can create whatever name you wish. HarryPotterCoin, VeryProfessionalBusinessCoin, whatever floats your boat. 
