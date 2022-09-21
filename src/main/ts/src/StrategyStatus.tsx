import { createContext, useContext, useState } from "react"
import React from 'react'

// Strategy status not shared via Context (renderer problem in React/see also useContextBridge)
export enum StrategyStatus {
	INITIALIZING,
	SUBSCRIBING_TO_STRATEGY,
	STRATEGY_RUNNING,
	STRATEGY_ERROR
}
