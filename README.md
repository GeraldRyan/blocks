# Yet another blockchain and crypto service! (beta)

Create a new blockchain with your friends. Use it as a cryptocurrency or any other form of data store, where data is precious. 

A blockchain is basically an open source, secure and reliable (under the right conditions) ledger, that can store data- for instance transactions. 

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

## FAQ For the Curious

### How is this secure? Why can't this be hacked? What gives? 

In short, it is based on mathematics. It would take far too much computing power to overpower the collective power of the community. Mining is too difficult for individuals, even with great computing power, to overpower the overal community. The community is made up of "nodes". You need not be a mining or verifying node to partake in the transactions. In fact most don't. It doesn't take a lot to make the system secure. By listening to given channels on our network (see below && We are using PubNub as our provider), you will hear of transactions dispatched (like the money you sent your brother) as well as succesful mining of blocks. Now would be a good time to talk about "mining" and of the chain. 

### What actually is mining, what is the blockchain? 

This will sound silly but the blockchain is just a chain of blocks! Next question. 

Ok kidding. But it is though. Ok what is a block? A block is a data structure that contains key information, such as its timestamp, it's hash, it's data payload (oh that's where the good stuff is), level of difficulty, as well as the hash value of its immediate predecessor, terminating at the genesis block, which is constant for every instance (version) of this blockchain app. If the genesis block of someone's chain is not valid, then her chain is not valid. If the block's overall data doesn't hash to a certain value that matches what it claims (why people can't fake data), then the block is invalid and so is the chain. If the block is valid in itself but its last hash does not correspond to the hash of the block it is supposed to follow, the chain is invalid. If a chain of someone's instance is valid, but a longer also valid chain appears, even if they disagree on the blocks belonging in that chain (which fyi will bev very very rare, and if it gets serious, can result in a 'fork" of a chainkwith each group going their own way (or particupating in both chains)

