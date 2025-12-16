# Machine Learning–Based Network Intrusion Detection System
## Overview

This project implements and evaluates a comprehensive machine learning–based intrusion detection system (IDS) using the UNSW-NB15 dataset, a modern benchmark designed to reflect realistic network traffic and contemporary cyberattacks. The goal was to systematically compare classical machine learning models, tree-based ensemble methods, and deep learning architectures to identify the most effective approaches for detecting malicious network activity.

The project emphasizes robust preprocessing, feature analysis, model comparison, and empirical evaluation, with a focus on practical IDS performance rather than purely theoretical results.

## Key Objectives

* Detect malicious network traffic with high accuracy and low false positives

* Compare linear, ensemble, and deep learning approaches on the same dataset

* Evaluate models using Accuracy, F1-Score, and ROC-AUC

* Identify models suitable for real-world IDS deployment

## Dataset

* UNSW-NB15 (Australian Centre for Cyber Security)

* ~82,000 network flow records

* Binary classification:

  * 0: Normal traffic

  * 1: Malicious traffic

* 9 attack families including DoS, Exploits, Reconnaissance, Shellcode, Worms, and more

* 39 numerical features after preprocessing

## Data Preprocessing

* Removal of non-informative identifiers

* Handling missing and infinite values

* Median imputation for numerical features

* One-hot encoding for selected categorical attributes

* Feature scaling (StandardScaler) for magnitude-sensitive models

* Outlier capping using statistical bounds

* Stratified 80/20 train-test split to preserve class balance

## Models Implemented
Classical Machine Learning

* Logistic Regression

* Naïve Bayes

* K-Nearest Neighbors (KNN)

* Support Vector Machine (RBF kernel)

Tree-Based Ensemble Methods

* Decision Tree

* Random Forest

* Extra Trees

* Gradient Boosting

* XGBoost

* LightGBM

* CatBoost

Deep Learning Models

* Multi-Layer Perceptron (MLP)

* Deep Neural Network (DNN – PyTorch)

* 1D Convolutional Neural Network (CNN)

* Transformer-based attention model

## Key Finding:
Tree-based ensemble models, particularly XGBoost and LightGBM, significantly outperform both classical and deep learning approaches on structured network traffic data.

## Technical Highlights

* End-to-end ML pipeline: preprocessing → feature analysis → modeling → evaluation

* Extensive model comparison across 15 algorithms

* Deep learning architectures implemented and trained from scratch

* Emphasis on interpretability vs. performance trade-offs

* Strong alignment with real-world IDS deployment considerations

## Tools & Technologies

* Python

* scikit-learn

* XGBoost / LightGBM / CatBoost

* PyTorch

* NumPy / Pandas

* Matplotlib / Seaborn

## Team

Patrick Kaggwa (creation of research paper pdf)

Pushya Damania (creation of all python code used for analysis)

Yewande Adegoroye (creation of research paper pdf)

Muhammad Khubayeeb Kabir (creation of presentation and slides)

## Future Work

* Multiclass attack classification

* Temporal modeling of network flows

* Adversarial robustness testing

* Hybrid ensemble + deep learning IDS architectures

## Why This Project Matters

* This project demonstrates applied expertise in:

* Machine learning for cybersecurity

* Large-scale model evaluation

* Working with realistic, noisy datasets

* Translating ML results into deployable system insights

It is representative of industry-relevant IDS research and practical ML engineering.
